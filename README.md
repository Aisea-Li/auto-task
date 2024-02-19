# auto-task  
基于mexc加密货币交易平台web接口的自动任务  
mexc主站地址: https://www.mexc.com  
mexc备用站地址: https://www.mexc.co (任务请求默认使用该地址)  
新用户注册: https://www.mexc.com/register?inviteCode=1Nvr6  

一、介绍  
1.自动参与阳关普照活动  
a.活动地址: https://www.mexc.com/zh-TW/sun/assessment  
b.持有至少1000MX达到一定时限可以参与活动，可以获得新项目空投  
c.默认频率五分钟一次  

2.小额加密货币(阳光普照空投,5-50u)出售  
a.默认排除部分币btc、eth、usdt、mx，其他可自行配置;  
b.当前价格小于最近30天最高价的90%立即卖出，卖出剩余执行小额兑换;  
c.当前价格小于最近30天最低开盘价立即执行小额兑换;  
d.默认频率5秒一次;  

3.小额加密货币(0-5u)自动兑换MX  
a.默认频率六小时一次;  

4.自动续期web token  
a.保证token不过期;  
b.默认频率一小时一次;  

二、使用方法  
1.推荐Linux机器部署;  
2.Linux服务器需要配置好jdk1.8+maven+git;  
3.git拉取到Liunx服务器, 执行sh deploy.sh, 服务会部署到/app/auto-task/下并启动执行;  
