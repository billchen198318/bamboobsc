A. Default login mode.
	copy applicationContext-shiro-default-sample.xml overwrite applicationContext-shiro.xml

==================================================================================================	
B. LDAP login mode.
	copy applicationContext-shiro-ldap-sample.xml overwrite applicationContext-shiro.xml
	and need settings success LDAP server address with DN value.
	
	example:
	
	<bean id="ldapContextFactory" class="org.apache.shiro.realm.ldap.JndiLdapContextFactory">
    	<property name="url" value="ldap://192.168.1.200:389" />    
    	<property name="systemUsername" value="cn=Manager,dc=example,dc=com" />
    	<property name="systemPassword" value="secret" />    	   
	</bean>
	
	<bean id="greenStepBaseAuthorizingLdapRealm" class="com.netsteadfast.greenstep.sys.GreenStepBaseAuthorizingLdapRealm">
   		<property name="userRoleService" ref="core.service.UserRoleService"/>
   		<property name="rolePermissionService" ref="core.service.RolePermissionService"/>
   		<property name="cacheManager" ref="shiroCacheManager"/>	
    	<property name="contextFactory" ref="ldapContextFactory" />
    	<property name="userDnTemplate" value="CN={0},OU=people,DC=netsteadfast,DC=com" />    	
	</bean> 
	
==================================================================================================	
C. ActiveDirectory login mode.
	copy applicationContext-shiro-ActiveDirectory-sample.xml overwrite applicationContext-shiro.xml
	and need settings success LDAP server address with DN value.
	
	example:
	
	<bean id="ldapContextFactory" class="org.apache.shiro.realm.ldap.JndiLdapContextFactory">
    	<property name="url" value="ldap://127.0.0.1:389" />       
    	<property name="systemUsername" value="cn=Manager,dc=example,dc=com" />
    	<property name="systemPassword" value="secret" />
	</bean>
	
	<bean id="greenStepBaseAuthorizingActiveDirectoryRealm" class="com.netsteadfast.greenstep.sys.GreenStepBaseAuthorizingActiveDirectoryRealm">
   		<property name="userRoleService" ref="core.service.UserRoleService"/>
   		<property name="rolePermissionService" ref="core.service.RolePermissionService"/>	
		<property name="cacheManager" ref="shiroCacheManager"/>
    	<property name="ldapContextFactory" ref="ldapContextFactory" />
    	<property name="searchBase" value="CN={0},OU=your-ou,DC=your-dc,DC=you-dc-com" />      
	</bean> 	
			