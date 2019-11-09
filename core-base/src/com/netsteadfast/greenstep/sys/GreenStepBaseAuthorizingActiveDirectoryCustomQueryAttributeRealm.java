/* 
 * Copyright 2012-2018 bambooCORE, greenstep of copyright Chen Xin Nien
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * -----------------------------------------------------------------------
 * 
 * author: 	Chen Xin Nien
 * contact: chen.xin.nien@gmail.com
 * 
 */
package com.netsteadfast.greenstep.sys;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.realm.ldap.LdapContextFactory;
import org.apache.shiro.realm.ldap.LdapUtils;

import com.netsteadfast.greenstep.base.Constants;

public class GreenStepBaseAuthorizingActiveDirectoryCustomQueryAttributeRealm extends GreenStepBaseAuthorizingActiveDirectoryRealm {
	
	public GreenStepBaseAuthorizingActiveDirectoryCustomQueryAttributeRealm() {
		super();
	}
	
    @Override
    protected AuthenticationInfo queryForAuthenticationInfo(AuthenticationToken token, LdapContextFactory ldapContextFactory) throws NamingException {
        final GreenStepBaseUsernamePasswordToken usernamePasswordToken = (GreenStepBaseUsernamePasswordToken) token;
        LdapContext ctx = null;
        /*
        try {
        	ctx = ldapContextFactory.getSystemLdapContext();
            final String attribName = "userPrincipalName";
            final SearchControls searchControls = new SearchControls(SearchControls.SUBTREE_SCOPE, 1, 0, new String[] { attribName }, false, false);
            final NamingEnumeration<SearchResult> search = ctx.search(searchBase, this.getCustomQueryAttributeValue(), new Object[] { usernamePasswordToken.getPrincipal() }, searchControls);
            if (search.hasMore()) {
            	final SearchResult next = search.next();
                String loginUser= next.getAttributes().get(attribName).get().toString();
                if (search.hasMore()) {
                    throw new RuntimeException("More than one user matching: "+usernamePasswordToken.getPrincipal());
                } else {
                    try {
                    	ldapContextFactory.getLdapContext(loginUser, usernamePasswordToken.getPassword());
                    } catch (Exception ex) {
                        throw ex;
                    }
                }
            }
            else {
                throw new RuntimeException("No user matching: " + usernamePasswordToken.getPrincipal());
            }
        } catch (NamingException ne) {
            throw ne;
        } finally {
            LdapUtils.closeContext(ctx);
        }
        */
        String searchBaseArr[] = StringUtils.defaultString(searchBase).split( Constants.ID_DELIMITER );
        boolean searchUser = false;
        for (int i = 0; searchBaseArr != null && !searchUser && i<searchBaseArr.length; i++) {
            try {
            	ctx = ldapContextFactory.getSystemLdapContext();
                final String attribName = "userPrincipalName";
                final SearchControls searchControls = new SearchControls(SearchControls.SUBTREE_SCOPE, 1, 0, new String[] { attribName }, false, false);
                final NamingEnumeration<SearchResult> search = ctx.search(searchBaseArr[i], this.getCustomQueryAttributeValue(), new Object[] { usernamePasswordToken.getPrincipal() }, searchControls);
                if (search.hasMore()) {
                	searchUser = true;
                	final SearchResult next = search.next();
                    String loginUser= next.getAttributes().get(attribName).get().toString();
                    if (search.hasMore()) {
                        throw new RuntimeException("More than one user matching: "+usernamePasswordToken.getPrincipal());
                    } else {
                        try {
                        	ldapContextFactory.getLdapContext(loginUser, usernamePasswordToken.getPassword());
                        } catch (Exception ex) {
                            throw ex;
                        }
                    }
                }
                /*
                else {
                    throw new RuntimeException("No user matching: " + usernamePasswordToken.getPrincipal());
                }
                */
            } catch (NamingException ne) {
                throw ne;
            } finally {
                LdapUtils.closeContext(ctx);
            }        	
        }
        if (!searchUser) {
        	throw new RuntimeException("No user matching: " + usernamePasswordToken.getPrincipal());
        }        
        return buildAuthenticationInfo(usernamePasswordToken.getUsername(), usernamePasswordToken.getPassword());
    }		
    
}
