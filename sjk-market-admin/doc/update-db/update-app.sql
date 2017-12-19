
-- 系统工具 系统
update App set subcatalog = ( select id from Catalog where name = '系统' and pid = 1  ) 
where exists (
    select 1 from MarketApp x
    where 
        catalog = 1 and subCatalogName = '系统工具' and marketName = 'eoemarket' 
        and marketAppId = id 
);
-- 生活娱乐 生活  
update App set subcatalog = ( select id from Catalog where name = '生活' and pid = 1  ) 
where exists (
    select 1 from MarketApp x
    where 
        catalog = 1 and subCatalogName = '生活娱乐' and marketName = 'eoemarket' 
        and marketAppId = id 
);
-- 社区交友 社交  
update App set subcatalog = ( select id from Catalog where name = '社交' and pid = 1  ) 
where exists (
    select 1 from MarketApp x
    where 
        subCatalogName = '社区交友' and marketName = 'eoemarket' 
        and marketAppId = id 
);
-- 网络通信 通讯  
update App set subcatalog = ( select id from Catalog where name = '通讯' and pid = 1  ) 
where exists (
    select 1 from MarketApp x
    where 
        catalog = 1 and subCatalogName = '网络通信' and marketName = 'eoemarket' 
        and marketAppId = id 
);
-- 小说漫画 图书  
update App set subcatalog = ( select id from Catalog where name = '图书' and pid = 1  ) 
where exists (
    select 1 from MarketApp x
    where 
        subCatalogName = '小说漫画' and marketName = 'eoemarket' 
        and marketAppId = id 
);
-- 铃声视频 影音  
update App set subcatalog = ( select id from Catalog where name = '影音' and pid = 1  ) 
where exists (
    select 1 from MarketApp x
    where 
        catalog = 1 and subCatalogName = '铃声视频' and marketName = 'eoemarket' 
        and marketAppId = id 
);
-- 桌面美化 美化  
update App set subcatalog = ( select id from Catalog where name = '美化' and pid = 1  ) 
where exists (
    select 1 from MarketApp x
    where 
        catalog = 1 and subCatalogName = '桌面美化' and marketName = 'eoemarket' 
        and marketAppId = id 
);
-- 商务财经 商业  
update App set subcatalog = ( select id from Catalog where name = '商业' and pid = 1  ) 
where exists (
    select 1 from MarketApp x
    where 
        catalog = 1 and subCatalogName = '商务财经' and marketName = 'eoemarket' 
        and marketAppId = id 
);
-- 系统工具 系统  
update App set subcatalog = ( select id from Catalog where name = '系统' and pid = 1  ) 
where exists (
    select 1 from MarketApp x
    where 
        catalog = 1 and subCatalogName = '系统工具' and marketName = 'eoemarket' 
        and marketAppId = id 
);



-- 飞行射击 飞行射击 
update App set subcatalog = ( select id from Catalog where name = '飞行射击' and pid = 2  ) 
where exists (
    select 1 from MarketApp x
    where 
        catalog = 2 and subCatalogName = '飞行射击' and marketName = 'eoemarket' 
        and marketAppId = id 
);
-- 动作冒险 动作冒险 
update App set subcatalog = ( select id from Catalog where name = '动作冒险' and pid = 2  ) 
where exists (
    select 1 from MarketApp x
    where 
        catalog = 2 and subCatalogName = '动作冒险' and marketName = 'eoemarket' 
        and marketAppId = id 
);
-- 益智休闲 休闲益智 
update App set subcatalog = ( select id from Catalog where name = '休闲益智' and pid = 2  ) 
where exists (
    select 1 from MarketApp x
    where 
        catalog = 2 and subCatalogName = '益智休闲' and marketName = 'eoemarket' 
        and marketAppId = id 
);
-- 角色扮演 角色养成 
update App set subcatalog = ( select id from Catalog where name = '角色养成' and pid = 2  ) 
where exists (
    select 1 from MarketApp x
    where 
        catalog = 2 and subCatalogName = '角色扮演' and marketName = 'eoemarket' 
        and marketAppId = id 
);
-- 棋牌天地 棋牌游戏 
update App set subcatalog = ( select id from Catalog where name = '棋牌游戏' and pid = 2  ) 
where exists (
    select 1 from MarketApp x
    where 
        catalog = 2 and subCatalogName = '棋牌天地' and marketName = 'eoemarket' 
        and marketAppId = id 
);
-- 经营策略 策略经营 
update App set subcatalog = ( select id from Catalog where name = '策略经营' and pid = 2  ) 
where exists (
    select 1 from MarketApp x
    where 
        catalog = 2 and subCatalogName = '经营策略' and marketName = 'eoemarket' 
        and marketAppId = id 
);
-- 体育竞技 竞速体育 
update App set subcatalog = ( select id from Catalog where name = '竞速体育' and pid = 2  ) 
where exists (
    select 1 from MarketApp x
    where 
        catalog = 2 and subCatalogName = '体育竞技' and marketName = 'eoemarket' 
        and marketAppId = id 
);
-- 网络游戏 其它 
update App set subcatalog = ( select id from Catalog where name = '其它' and pid = 2  ) 
where exists (
    select 1 from MarketApp x
    where 
        catalog = 2 and subCatalogName = '网络游戏' and marketName = 'eoemarket' 
        and marketAppId = id 
);