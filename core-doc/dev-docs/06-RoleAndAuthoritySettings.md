<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/00-Catalog.md">⌂ Catalog</a><br/>
<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/05-ProgramRegistrationAndMenuSettings.md">⇦ Previous section 05-Role and authority settings</a>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/07-ExpressionSupportLogicService.md">⇨ Next section 07-Expression support LogicService</a>


# 06 - Role and authority settings 
# Introduction
bambooBSC role and authority settings.<br>

**super role no need do all settings, it can use all function and view all menu-item.**

now system found role list

| ROLE-ID | description |
| --- | --- |
| * | super role, equals admin |
| admin | super role, equals * |
| BSC_STANDARD | default use role, cannot delete account |
| HR_MANAGE | for HR-manager, can delete account |
| VIEW_ONLY_CLASS01 | for view only, no create/update/delete permission |

when create a account, the account role default is `BSC_STANDARD` <br/>
Parameter config: 
```SQL
select PARAM1 from tb_sys_code where CODE='BSC_CONF001'
```

# Role management

1. click `01 - Role`
![Image of Role-mgr1](https://raw.githubusercontent.com/billchen198318/bamboobsc/master/core-doc/dev-docs/pics/06-001.jpg)
<br>
<br>

***`admin` and * Role is super role, super role cannot share copy as***

# Role's permitted settings 

![Image of Role-mgr2](https://raw.githubusercontent.com/billchen198318/bamboobsc/master/core-doc/dev-docs/pics/06-002.jpg)

<br>
<br>

***`admin` and * Role is super role, super role no need permitted settings, super role can use all function***


# Config user role
1. click `02 - User's role` to settings
![Image of Role-mgr3](https://raw.githubusercontent.com/billchen198318/bamboobsc/master/core-doc/dev-docs/pics/06-003.jpg)

<br>
<br>

# Config menu item role
1. click `03 - Role for program (menu) ` to settings
![Image of Role-mgr4](https://raw.githubusercontent.com/billchen198318/bamboobsc/master/core-doc/dev-docs/pics/06-004.jpg)

<br>
<br>

***`admin` and * Role is super role, super role no need config***
