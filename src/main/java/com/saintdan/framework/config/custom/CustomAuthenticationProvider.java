package com.saintdan.framework.config.custom;

import com.saintdan.framework.component.CustomPasswordEncoder;
import com.saintdan.framework.component.LogHelper;
import com.saintdan.framework.domain.UserDomain;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.exception.IllegalTokenTypeException;
import com.saintdan.framework.po.OauthAccessToken;
import com.saintdan.framework.po.User;
import com.saintdan.framework.repo.UserRepository;
import com.saintdan.framework.tools.Assert;
import com.saintdan.framework.tools.LogUtils;
import com.saintdan.framework.tools.LoginUtils;
import com.saintdan.framework.tools.RemoteAddressUtils;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

/**
 * Custom authentication provider.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 12/9/15
 * @since JDK1.8
 */
@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

  @Override public Authentication authenticate(Authentication authentication)
      throws AuthenticationException {
    UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
    String clientId;
    try {
      clientId = LoginUtils.getClientId(request);
    } catch (IllegalTokenTypeException e) {
      throw new BadCredentialsException(ErrorType.LOG0007.name());
    }
    // Find user.
    String username = token.getName();
    User user = userDomain.findByAccount(username);
    TokenStore tokenStore = customTokenStore.customTokenStore();
    if (user == null) {
      throw new BadCredentialsException(ErrorType.LOG0001.name());
    }
    // Compare password and credentials of authentication.
    if (!customPasswordEncoder.matches(token.getCredentials().toString(), user.getPwd())) {
      throw new BadCredentialsException(ErrorType.LOG0002.name());
    }
    // Valid account.
    if (!user.isEnabled()) {
      throw new BadCredentialsException(ErrorType.LOG0003.name());
    } else if (!user.isAccountNonExpired()) {
      throw new BadCredentialsException(ErrorType.LOG0004.name());
    } else if (!user.isAccountNonLocked()) {
      throw new BadCredentialsException(ErrorType.LOG0005.name());
    } else if (!user.isCredentialsNonExpired()) {
      throw new BadCredentialsException(ErrorType.LOG0006.name());
    }
    // Get client ip address.
    String ip = RemoteAddressUtils.getRealIp(request);
    // Delete token if repeat login.
    if (user.getIp() != null && !user.getIp().equals(ip)) {
      tokenStore.findTokensByClientIdAndUserName(clientId, username).stream()
          .map(oAuth2AccessToken -> {
            tokenStore.removeAccessToken(oAuth2AccessToken);
            tokenStore.removeRefreshToken(oAuth2AccessToken.getRefreshToken());
            return null;
          });
    }
    // Save user login info.
    user.setIp(ip);
    user.setLastLoginAt(System.currentTimeMillis());
    userRepository.save(user);
    // Save to log.
    try {
      final String LOGIN = "login";
      logHelper.log(HttpMethod.POST, username, ip, clientId, LOGIN);
    } catch (Exception e) {
      final String errMsg = "Log user login failed.";
      LogUtils.traceError(logger, e, errMsg);
    }
    //Authorize.
    return new UsernamePasswordAuthenticationToken(user, user.getPwd(), user.getAuthorities());
  }

  @Override public boolean supports(Class<?> authentication) {
    return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
  }

  private final UserRepository userRepository;
  private final HttpServletRequest request;
  private final UserDomain userDomain;
  private final LogHelper logHelper;
  private final CustomPasswordEncoder customPasswordEncoder;
  private final CustomTokenStore customTokenStore;
  private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

  @Autowired public CustomAuthenticationProvider(
      UserRepository userRepository,
      HttpServletRequest request,
      Environment environment,
      UserDomain userDomain,
      LogHelper logHelper,
      CustomPasswordEncoder customPasswordEncoder,
      CustomTokenStore customTokenStore) {
    Assert.defaultNotNull(userRepository);
    Assert.defaultNotNull(request);
    Assert.defaultNotNull(environment);
    Assert.defaultNotNull(userDomain);
    Assert.defaultNotNull(logHelper);
    Assert.defaultNotNull(customPasswordEncoder);
    Assert.defaultNotNull(customTokenStore);
    this.userRepository = userRepository;
    this.request = request;
    this.userDomain = userDomain;
    this.logHelper = logHelper;
    this.customPasswordEncoder = customPasswordEncoder;
    this.customTokenStore = customTokenStore;
  }
}
