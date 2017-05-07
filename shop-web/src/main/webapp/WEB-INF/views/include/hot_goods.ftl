<div class="hotGoods">
    <dl>
        <dt>热销商品</dt>

    [@hot_goods_list count=3 tagId=3]
        [#if hotGoodsList?has_content]
            [#list hotGoodsList as hotGoods]
                <dd>
                    <a href="${ctx}/goods/detail/${hotGoods.id}">
                        <img src="${hotGoods.image}" alt="${hotGoods.name}" />
                        <span title="${hotGoods.name}">${hotGoods.name}</span>
                    </a>
                    <strong>
                        ￥${hotGoods.price}
                        <del>￥${hotGoods.marketPrice}</del>
                    </strong>
                </dd>
            [/#list]
        [/#if]
    [/@hot_goods_list]
    </dl>
</div>