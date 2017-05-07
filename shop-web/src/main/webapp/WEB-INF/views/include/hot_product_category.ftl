
<div class="hotProductCategory">
[@product_category_root_list count = 6]
    [#if productCategories?has_content ]
        [#list productCategories as productCategory ]
            <dl class="odd clearfix">
                <dt>
                    <a href="${ctx}/goods/list/${productCategory.id}">${productCategory.name}</a>
                </dt>
                [@product_category_children_list productCategoryId = productCategory.id count=3]
                    [#if productChildrenCategories?has_content ]
                        [#list productChildrenCategories as productCategory ]
                            <dd>
                                <a href="${ctx}/goods/list/${productCategory.id}">
                                ${productCategory.name}
                                </a>
                            </dd>
                        [/#list]
                    [/#if]
                [/@product_category_children_list]
            </dl>
        [/#list]
    [/#if]
[/@product_category_root_list]
</div>