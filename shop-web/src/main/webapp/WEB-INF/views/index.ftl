<!DOCTYPE html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<link href="${ctx}/favicon.ico" rel="icon" type="image/x-icon" />
	<link href="${ctx}/slider/slider.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.tools.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.lazyload.js"></script>
	<script type="text/javascript" src="${ctx}/slider/slider.js"></script>
	<script type="text/javascript" src="${ctx}/js/common.js"></script>
	<style type="text/css">
		.header {
			margin-bottom: 0px;
		}
	</style>
<script type="text/javascript">
$().ready(function() {

	var $productCategoryMenuItem = $("#productCategoryMenu li");
	var $slider = $("#slider");
	var $newArticleTab = $("#newArticle ul.tab");
	var $hotGoodsImage = $("div.hotGoods img");
	
	$productCategoryMenuItem.hover(
		function() {
			$(this).children("div.menu").show();
		}, function() {
			$(this).children("div.menu").hide();
		}
	);
	
	$slider.nivoSlider({
		effect: "random",
		animSpeed: 1000,
		pauseTime: 6000,
		controlNav: true,
		keyboardNav: false,
		captionOpacity: 0.4
	});
	
	$newArticleTab.tabs("ul.tabContent", {
		tabs: "li",
		event: "mouseover"
	});
	
	$hotGoodsImage.lazyload({
		threshold: 100,
		effect: "fadeIn",
		skip_invisible: false
	});

});
</script>
</head>
<body>
<script type="text/javascript">
$().ready(function() {

	var $headerName = $("#headerName");
	var $headerLogin = $("#headerLogin");
	var $headerRegister = $("#headerRegister");
	var $headerLogout = $("#headerLogout");
	var $goodsSearchForm = $("#goodsSearchForm");
	var $keyword = $("#goodsSearchForm input");
	var defaultKeyword = "商品搜索";
	
	var username = getCookie("username");
	var nickname = getCookie("nickname");
	if ($.trim(nickname) != "") {
		$headerName.text(nickname).show();
		$headerLogout.show();
	} else if ($.trim(username) != "") {
		$headerName.text(username).show();
		$headerLogout.show();
	} else {
		$headerLogin.show();
		$headerRegister.show();
	}
	
	$keyword.focus(function() {
		if ($.trim($keyword.val()) == defaultKeyword) {
			$keyword.val("");
		}
	});
	
	$keyword.blur(function() {
		if ($.trim($keyword.val()) == "") {
			$keyword.val(defaultKeyword);
		}
	});
	
	$goodsSearchForm.submit(function() {
		if ($.trim($keyword.val()) == "" || $keyword.val() == defaultKeyword) {
			return false;
		}
	});

});
</script>
	[#--顶部--]
[#include "include/header.ftl" /]

<div class="container index">
		<div class="row">
			<div class="span2">
					<div id="productCategoryMenu" class="productCategoryMenu">
						<ul>
						
						[@product_category_root_list count = 6]
								[#if productCategories?has_content ]
									[#list productCategories as productCategory ]
										<li>
											<div class="item">
												<div>
													[#--获取一级菜单--]
													[@product_category_children_list productCategoryId = productCategory.id count=3]
														[#if productChildrenCategories?has_content ]
															[#list productChildrenCategories as productCategory ]
																<a href="${ctx}/goods/list/${productCategory.id}">
																	<strong>${productCategory.name}</strong>
																</a>
															[/#list]
														[/#if]
													[/@product_category_children_list]
												</div>
												<div>
													[@brand_list productCategoryId = productCategory.id count=4]
														[#if brands?has_content ]
															[#list brands as brand ]
																<a href="${ctx}/goods/list/${productCategory.id}?brandId=${brand.id}">
																	${brand.name}
																</a>
															[/#list]
														[/#if]
													[/@brand_list]
												</div>
											</div>
											
											[#--展开分类--]
											<div class="menu">
												[@product_category_children_list productCategoryId = productCategory.id count=7]
													[#if productChildrenCategories?has_content ]
														[#list productChildrenCategories as productCategory ]
															<dl class="clearfix[#if !productCategory_has_next] last[/#if]">
																<dt>
																	<a href="${ctx}/goods/list/${productCategory.id}">${productCategory.name}</a>
																</dt>
																[@product_category_children_list productCategoryId = productCategory.id count=7]
																	[#list productChildrenCategories as productCategory ]
																		<dd>
																			<a href="${ctx}/goods/list/${productCategory.id}">${productCategory.name}</a>[#if productCategory_has_next]|[/#if]
																		</dd>
																	[/#list]
																[/@product_category_children_list]
															</dl>
														[/#list]
													[/#if]
												[/@product_category_children_list]
												
											<div class="auxiliary">
												[@brand_list productCategoryId = productCategory.id count = 8]
													[#if brands?has_content ]
														<div>
															<strong>推荐品牌</strong>
															[#list brands as brand]
																<a href="${ctx}/goods/list/${productCategory.id}?brandId=${brand.id}">${brand.name}</a>
															[/#list]
														</div>
													[/#if]
												[/@brand_list]
												[@promotion_list productCategoryId = productCategory.id hasEnded = false count = 4]
													[#if promotions?has_content]
														<div>
															<strong>热门促销</strong>
															[#list promotions as promotion]
																[#if promotion.image?has_content]
																	<a href="${ctx}/promotion/content/${promotion.id}" title="${promotion.title}">
																		<img src="${promotion.image}" alt="${promotion.title}" />
																	</a>
																[/#if]
															[/#list]
														</div>
													[/#if]
												[/@promotion_list]
											</div>
										</div>
											
										</li>
									[/#list]
								[/#if]
						[/@product_category_root_list]
							
								
						</ul>
					</div>
			</div>
				[#--广告位--]
			<div class="span10">
					[@ad_position id=1 /]
			</div>
		</div>
		
		
		[#--第二个广告位--]
		<div class="row">
			<div class="span9">
				[@ad_position id=2 /]
			</div>
			<div class="span3">
				[@article_category_root_list count=2]
					<div id="newArticle" class="newArticle">
					[#if articleCategories?has_content]
						<ul class="tab">
							[#list articleCategories as articleCategory]
								<li>
									<a href="${ctx}/article_category/${articleCategory.id}" target="_blank">${articleCategory.name}</a>
								</li>
							[/#list]
						</ul>
						
						[#--获取大的分类的各个文章--]
						[#list articleCategories as articleCategory ]
						[@article_list categoryId=articleCategory.id count= 6 ]
							<ul class="tabContent">
								[#list articles as article ]
									<li>
										<a href="${ctx}/article/${article.id}" title="${article.title}" target="_blank">${article.title}</a>
									</li>
								[/#list]
							</ul>
						[/@article_list]
					[/#list]
					
				[/#if]
			[/@article_category_root_list]
					
				</div>
			</div>
		</div>
		<div class="row">
			<div class="span12">
				[@ad_position id=3 /]
			</div>
			</div>
[#--热门商品展示--]
[#--获取三个分类--]
[@product_category_root_list count = 3]
[#--获取分栏广告赋值给变量：adIterator--]
	[@ad_position id = 4]
		[#if adPosition??]
			[#assign adIterator = adPosition.ads.iterator() /]
		[/#if]
	[/@ad_position]
[#--循环分类--]
	[#list productCategories as productCategory ]
        <div class="row">
            <div class="span12">
                <div class="hotGoods">
                    <dl class="title${productCategory_index + 1}">
                        <dt>
                            <a href="${ctx}/goods/list/${productCategory.id}">${productCategory.name}</a>
                        </dt>
					[#--获取到子分类--]
						[@product_category_children_list productCategoryId=productCategory.id count=7]
							[#list productChildrenCategories as productChildrenCategory ]
                                <dd>
                                    <a href="${ctx}/goods/list/${productChildrenCategory.id}">${productChildrenCategory.name}</a>
                                </dd>
							[/#list]
						[/@product_category_children_list]

                    </dl>

				[#--广告位--]
					[#if adIterator?? && adIterator.hasNext()]
						[#assign ad = adIterator.next() /]
                        <div>
							[#if ad.url??]
                                <a href="${ad.url}">
                                    <img src="${ad.path}" alt="${ad.title}" title="${ad.title}" />
                                </a>
							[#else]
                                <img src="${ad.path}" alt="${ad.title}" title="${ad.title}" />
							[/#if]
                        </div>
					[/#if]

				[#--分类下的热门商品开始--]
					[@goods_list productCategoryId = productCategory.id count = 10 tagId = 3]
                        <ul>
							[#list goodsList as good ]
								[#if good_index < 5 ]
                                    <li>
                                        <a href="${ctx}/goods/content/${good.id}" title="${good.name}" target="_blank">
                                            <div>
                                                <span title="${good.name}">${good.name}</span>
                                                <em title="${good.caption}">${good.caption}</em>
                                            </div>
                                            <strong>￥${good.price}</strong>
                                            <img src="${ctx}/upload/image/blank.gif" data-original="${good.image}" />
                                        </a>
                                    </li>
								[#else]
                                    <li class="low">
                                        <a href="${ctx}/goods/content/${good.id}" title="${good.name}" target="_blank">
                                            <img src="${ctx}/upload/image/blank.gif" data-original="${good.image}" />
                                            <span title="${good.name}">${good.name}</span>
                                            <strong>￥${good.price}</strong>
                                        </a>
                                    </li>
								[/#if]
							[/#list]
                        </ul>
					[/@goods_list]
                </div>
            </div>
        </div>
	[/#list]
[/@product_category_root_list]

		<div class="row">
			<div class="span12">
				[@ad_position id = 5 /]
			</div>
		</div>
		
		
		<div class="row">
			<div class="span12">
				[@brand_list type = 1 count = 10]
					[#if brands?has_content]
						<div class="hotBrand">
							<ul class="clearfix">
								[#list brands as brand]
									<li>
										<a href="${ctx}${brand.path}" title="${brand.name}">
											<img src="${brand.logo}" alt="${brand.name}" />
										</a>
									</li>
								[/#list]
							</ul>
						</div>
					[/#if]
				[/@brand_list]
			</div>
		</div>
		
	</div>
	[#include "include/footer.ftl" /]
</body>
</html>