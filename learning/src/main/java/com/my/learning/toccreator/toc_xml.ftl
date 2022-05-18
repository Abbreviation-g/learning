<?xml version="1.0" encoding="UTF-8"?>
<?NLS TYPE="org.eclipse.help.toc"?>

<toc label="可视化自动单元与集成测试平台SunwiseAUnit" topic="${htmlPath}">
   <#list firstCatalogs as catalog>
   <topic href="${htmlPath}${catalog.toc}" label="${catalog.title}">
        <#list catalog.childCatalogs as secendCatalog>
        <topic href="${htmlPath}${secendCatalog.toc}" label="${secendCatalog.title}">
            <#list secendCatalog.childCatalogs as thirdCatalog>
            <topic href="${htmlPath}${thirdCatalog.toc}" label="${thirdCatalog.title}">
            </topic>
            </#list>
        </topic>
        </#list>
   </topic>
   </#list>
</toc>
