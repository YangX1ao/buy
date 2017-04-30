<!DOCTYPE html >
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link href="/css/common.css" rel="stylesheet" type="text/css" />
<link href="/css/goods.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/js/jquery.js"></script>
<script type="text/javascript" src="/js/jquery.lazyload.js"></script>
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $headerCart = $("#headerCart");
	var $compareBar = $("#compareBar");
	var $compareForm = $("#compareBar form");
	var $compareSubmit = $("#compareBar a.submit");
	var $clearCompare = $("#compareBar a.clear");
	var $goodsForm = $("#goodsForm");
	var $orderType = $("#orderType");
	var $pageNumber = $("#pageNumber");
	var $pageSize = $("#pageSize");
	var $gridType = $("#gridType");
	var $listType = $("#listType");
	var $size = $("#layout a.size");
	var $previousPage = $("#previousPage");
	var $nextPage = $("#nextPage");
	var $sort = $("#sort a, #sort li");
	var $orderMenu = $("#orderMenu");
	var $startPrice = $("#startPrice");
	var $endPrice = $("#endPrice");
	var $result = $("#result");
	var $productImage = $("#result img");
	var $addCart = $("#result a.addCart");
	var $exchange = $("#result a.exchange");
	var $addFavorite = $("#result a.addFavorite");
	var $addCompare = $("#result a.addCompare");
	
	var layoutType = getCookie("layoutType");
	if (layoutType == "listType") {
		$listType.addClass("currentList");
		$result.removeClass("grid").addClass("list");
	} else {
		$gridType.addClass("currentGrid");
		$result.removeClass("list").addClass("grid");
	}
	
	$gridType.click(function() {
		var $this = $(this);
		if (!$this.hasClass("currentGrid")) {
			$this.addClass("currentGrid");
			$listType.removeClass("currentList");
			$result.removeClass("list").addClass("grid");
			addCookie("layoutType", "gridType");
		}
		return false;
	});
	
	$listType.click(function() {
		var $this = $(this);
		if (!$this.hasClass("currentList")) {
			$this.addClass("currentList");
			$gridType.removeClass("currentGrid");
			$result.removeClass("grid").addClass("list");
			addCookie("layoutType", "listType");
		}
		return false;
	});
	
	$size.click(function() {
		var $this = $(this);
		$pageNumber.val(1);
		var pageSize = $this.attr("pageSize");
		$pageSize.val(pageSize);
		$goodsForm.submit();
		return false;
	});
	
	$previousPage.click(function() {
		$pageNumber.val(0);
		$goodsForm.submit();
		return false;
	});
	
	$nextPage.click(function() {
		$pageNumber.val(2);
		$goodsForm.submit();
		return false;
	});
	
	$orderMenu.hover(
		function() {
			$(this).children("ul").show();
		}, function() {
			$(this).children("ul").hide();
		}
	);
	
	$sort.click(function() {
		var $this = $(this);
		if ($this.hasClass("current")) {
			$orderType.val("");
		} else {
			$orderType.val($this.attr("orderType"));
		}
		$pageNumber.val(1);
		$goodsForm.submit();
		return false;
	});
	
	$startPrice.add($endPrice).focus(function() {
		$(this).siblings("button").show();
	});
	
	$startPrice.add($endPrice).keypress(function(event) {
		return (event.which >= 48 && event.which <= 57) || (event.which == 46 && $(this).val().indexOf(".") < 0) || event.which == 8 || event.which == 13;
	});
	
	$goodsForm.submit(function() {
		if ($orderType.val() == "" || $orderType.val() == "topDesc") {
			$orderType.prop("disabled", true);
		}
		if ($pageNumber.val() == "" || $pageNumber.val() == "1") {
			$pageNumber.prop("disabled", true);
		}
		/*
		if ($pageSize.val() == "" || $pageSize.val() == "20") {
			$pageSize.prop("disabled", true);
		}*/
		if ($startPrice.val() == "" || !/^\d+(\.\d+)?$/.test($startPrice.val())) {
			$startPrice.prop("disabled", true);
		}
		if ($endPrice.val() == "" || !/^\d+(\.\d+)?$/.test($endPrice.val())) {
			$endPrice.prop("disabled", true);
		}
		if ($goodsForm.serializeArray().length < 1) {
			location.href = location.pathname;
			return false;
		}
	});
	
	$productImage.lazyload({
		threshold: 100,
		effect: "fadeIn"
	});
	
	// 加入购物车
	$addCart.click(function() {
		var $this = $(this);
		var productId = $this.attr("productId");
		$.ajax({
			url: "/cart/add",
			type: "POST",
			data: {goodsId: productId, quantity: 1},
			dataType: "json",
			cache: false,
			success: function(message) {
				if (message.resultCode == 1) {
					var $image = $this.closest("li").find("img");
					var cartOffset = $headerCart.offset();
					var imageOffset = $image.offset();
					$image.clone().css({
						width: 170,
						height: 170,
						position: "absolute",
						"z-index": 20,
						top: imageOffset.top,
						left: imageOffset.left,
						opacity: 0.8,
						border: "1px solid #dddddd",
						"background-color": "#eeeeee"
					}).appendTo("body").animate({
						width: 30,
						height: 30,
						top: cartOffset.top,
						left: cartOffset.left,
						opacity: 0.2
					}, 1000, function() {
						$(this).remove();
					});
					$.message('success', message.result);
					$headerCart.find('em').html(parseInt($headerCart.find('em').html()) + 1);
				} else {
					$.message('error', message.resultMessage);
				}
			}
		});
		return false;
	});
	
	// 积分兑换
	$exchange.click(function() {
		var productId = $(this).attr("productId");
		$.ajax({
			url: "/shopxx/order/check_exchange.jhtml",
			type: "GET",
			data: {productId: productId, quantity: 1},
			dataType: "json",
			cache: false,
			success: function(message) {
				if (message.type == "success") {
					location.href = "/shopxx/order/checkout.jhtml?type=exchange&productId=" + productId + "&quantity=1";
				} else {
					$.message(message);
				}
			}
		});
		return false;
	});
	
	// 添加商品收藏
	$addFavorite.click(function() {
		var goodsId = $(this).attr("goodsId");
		$.ajax({
			url: "/shopxx/member/favorite/add.jhtml",
			type: "POST",
			data: {goodsId: goodsId},
			dataType: "json",
			cache: false,
			success: function(message) {
				$.message(message);
			}
		});
		return false;
	});
	
	// 对比栏
	var compareGoods = getCookie("compareGoods");
	var compareGoodsIds = compareGoods != null ? compareGoods.split(",") : [];
	if (compareGoodsIds.length > 0) {
		$.ajax({
			url: "/shopxx/goods/compare_bar.jhtml",
			type: "GET",
			data: {goodsIds: compareGoodsIds},
			dataType: "json",
			cache: true,
			success: function(data) {
				$.each(data, function (i, item) {
					var thumbnail = item.thumbnail != null ? item.thumbnail : "/shopxx/upload/image/default_thumbnail.jpg";
					$compareBar.find("dt").after(
'<dd> <input type="hidden" name="goodsIds" value="' + item.id + '" \/> <a href="' + escapeHtml(item.url) + '" target="_blank"> <img src="' + escapeHtml(thumbnail) + '" \/> <span title="' + escapeHtml(item.name) + '">' + escapeHtml(abbreviate(item.name, 50)) + '<\/span> <\/a> <strong>' + currency(item.price, true) + '<del>' + currency(item.marketPrice, true) + '<\/del><\/strong> <a href="javascript:;" class="remove" goodsId="' + item.id + '">[删除]<\/a> <\/dd>'					);
				});
				$compareBar.fadeIn();
			}
		});
		
		$.each(compareGoodsIds, function(i, goodsId) { 
			$addCompare.filter("[goodsId='" + goodsId + "']").addClass("selected");
		});
	}
	
	// 移除对比
	$compareBar.on("click", "a.remove", function() {
		var $this = $(this);
		var goodsId = $this.attr("goodsId");
		$this.closest("dd").remove();
		for (var i = 0; i < compareGoodsIds.length; i ++) {
			if (compareGoodsIds[i] == goodsId) {
				compareGoodsIds.splice(i, 1);
				break;
			}
		}
		$addCompare.filter("[goodsId='" + goodsId + "']").removeClass("selected");
		if (compareGoodsIds.length == 0) {
			$compareBar.fadeOut();
			removeCookie("compareGoods");
		} else {
			addCookie("compareGoods", compareGoodsIds.join(","));
		}
		return false;
	});
	
	$compareSubmit.click(function() {
		if (compareGoodsIds.length < 2) {
			$.message("warn", "至少需要两个对比商品");
			return false;
		}
		
		$compareForm.submit();
		return false;
	});
	
	// 清除对比
	$clearCompare.click(function() {
		$addCompare.removeClass("selected");
		$compareBar.fadeOut().find("dd:not(.action)").remove();
		compareGoodsIds = [];
		removeCookie("compareGoods");
		return false;
	});
	
	// 添加对比
	$addCompare.click(function() {
		var $this = $(this);
		var goodsId = $this.attr("goodsId");
		if ($.inArray(goodsId, compareGoodsIds) >= 0) {
			return false;
		}
		if (compareGoodsIds.length >= 4) {
			$.message("warn", "最多只允许添加4个对比商品");
			return false;
		}
		$.ajax({
			url: "/shopxx/goods/add_compare.jhtml",
			type: "GET",
			data: {goodsId: goodsId},
			dataType: "json",
			cache: false,
			success: function(data) {
				if (data.message.type == "success") {
					$this.addClass("selected");
					var thumbnail = data.thumbnail != null ? data.thumbnail : "/shopxx/upload/image/default_thumbnail.jpg";
					$compareBar.show().find("dd.action").before(
'<dd> <input type="hidden" name="goodsIds" value="' + data.id + '" \/> <a href="' + escapeHtml(data.url) + '" target="_blank"> <img src="' + escapeHtml(thumbnail) + '" \/> <span title="' + escapeHtml(data.name) + '">' + escapeHtml(abbreviate(data.name, 50)) + '<\/span> <\/a> <strong>' + currency(data.price, true) + '<del>' + currency(data.marketPrice, true) + '<\/del><\/strong> <a href="javascript:;" class="remove" goodsId="' + data.id + '">[删除]<\/a> <\/dd>'					);
					compareGoodsIds.unshift(goodsId);
					addCookie("compareGoods", compareGoodsIds.join(","));
				} else {
					$.message(data.message);
				}
			}
		});
		return false;
	});
	
	$.pageSkip = function(pageNumber) {
		$pageNumber.val(pageNumber);
		$goodsForm.submit();
		return false;
	}

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
[#include "include/header.ftl" /]

	<div class="container goodsList">
		<div id="compareBar" class="compareBar">
			<form action="/shopxx/goods/compare.jhtml" method="get">
				<dl>
					<dt>对比栏</dt>
					<dd class="action">
						<a href="javascript:;" class="submit">对 比</a>
						<a href="javascript:;" class="clear">清 空</a>
					</dd>
				</dl>
			</form>
		</div>
		<div class="row">
			<div class="span2">
<div class="hotProductCategory">
		[@product_category_root_list count=6]
			[#if productCategories?has_content]
				[#list productCategories as productCategory]
					<dl class="odd clearfix">
				<dt>
					<a href="${ctx}/goods/list/${productCategory.id}">${productCategory.name}</a>
				</dt>
					[@product_category_children_list productCategoryId=productCategory.id count=3]
						[#if productChildrenCategories?has_content]
							[#list productChildrenCategories as productChildrenCategory]
								<dd>
									<a href="${ctx}/goods/list/${productChildrenCategory.id}">${productChildrenCategory.name}</a>
								</dd>
							[/#list]
						[/#if]
					[/@product_category_children_list]
					
			</dl>
				[/#list]
			[/#if]
		[/@product_category_root_list]
				

</div>	<div class="hotBrand clearfix">
		<dl>
			<dt>热门品牌</dt>
		[@brand_list count=6]
				[#list brands as brand]
						<dd [#if brand_index%2!=0] class="even"[/#if]>
							<a href="${ctx}/brand/${brand.id}" title="${brand.name}">
								<img src="${brand.logo}" alt="${brand.name}" />
								<span>${brand.name}</span>
							</a>
						</dd>
				[/#list]
		[/@brand_list]
		</dl>
		
				
	</div>
	<div class="hotGoods">
		<dl>
			<dt>热销商品</dt>
				<dd>
					<a href="/goods/content/1">
						<img src="http://image.demo.shopxx.net/4.0/201501/e39f89ce-e08a-4546-8aee-67d4427e86e2-thumbnail.jpg" alt="苹果 iPhone 5s" />
						<span title="苹果 iPhone 5s">苹果 iPhone 5s</span>
					</a>
					<strong>
						￥4200
							<del>￥5040</del>
					</strong>
				</dd>
				<dd>
					<a href="/goods/content/2">
						<img src="http://image.demo.shopxx.net/4.0/201501/d7f59d79-1958-4059-852c-0d6531788b48-thumbnail.jpg" alt="苹果 iPhone 6" />
						<span title="苹果 iPhone 6">苹果 iPhone 6</span>
					</a>
					<strong>
						￥5200
							<del>￥6240</del>
					</strong>
				</dd>
				<dd>
					<a href="/goods/content/3">
						<img src="http://image.demo.shopxx.net/4.0/201501/031e30a4-6237-4650-a14c-5132aa126acd-thumbnail.jpg" alt="三星 G3818" />
						<span title="三星 G3818">三星 G3818</span>
					</a>
					<strong>
						￥1200
							<del>￥1440</del>
					</strong>
				</dd>
		</dl>
	</div>
	<div class="hotPromotion">
		<dl>
			<dt>热销促销</dt>
				<dd>
					<a href="/promotion/1" title="iPhone促销专场">
						<img src="http://image.demo.shopxx.net/4.0/201501/0a1ceb47-f51c-4dfb-9d51-cb723c2f8e78.jpg" alt="iPhone促销专场" />
					</a>
				</dd>
				<dd>
					<a href="/promotion/2" title="联想笔记本促销专场">
						<img src="http://image.demo.shopxx.net/4.0/201501/98229d3b-08b7-4888-a99e-cf21a2a03b65.jpg" alt="联想笔记本促销专场" />
					</a>
				</dd>
				<dd>
					<a href="/promotion/3" title="平板电视促销专场">
						<img src="http://image.demo.shopxx.net/4.0/201501/2b71bacf-bd18-46fb-adab-072dd544ed66.jpg" alt="平板电视促销专场" />
					</a>
				</dd>
		</dl>
	</div>
			</div>
			<div class="span10">
				<div class="breadcrumb">
					<ul>
						<li>
							<a href="/index">首页</a>
						</li>
							<li>搜索 &quot;苹果&quot; 结果列表</li>
					</ul>
				</div>
				<form id="goodsForm" action="/goods/search" method="get">
					<input type="hidden" id="keyword" name="keyword" value="苹果" />
					<input type="hidden" id="orderType" name="sort" value="create_date.desc" />
					<input type="hidden" id="pageNumber" name="page" value="1" />
					<input type="hidden" id="pageSize" name="pageSize" value="3" />
					<div class="bar">
						<div id="layout" class="layout">
							<label>布局:</label>
							<a href="javascript:;" id="gridType" class="gridType">
								<span>&nbsp;</span>
							</a>
							<a href="javascript:;" id="listType" class="listType">
								<span>&nbsp;</span>
							</a>
							<label>数量:</label>
							<a href="javascript:;" class="size" pageSize="20">
								<span>20</span>
							</a>
							<a href="javascript:;" class="size" pageSize="40">
								<span>40</span>
							</a>
							<a href="javascript:;" class="size" pageSize="80">
								<span>80</span>
							</a>
							<span class="page">
								<label>共9个商品 2/3</label>
								<a href="javascript:;" id="previousPage" class="previousPage">
									<span>上一页</span>
								</a>
								<a href="javascript:;" id="nextPage" class="nextPage">
									<span>下一页</span>
								</a>
							</span>
						</div>
						<div id="sort" class="sort">
							<div id="orderMenu" class="orderMenu">
									<span>日期降序</span>
								<ul>
										<li orderType="is_top.desc">置顶降序</li>
										<li orderType="price.asc">价格升序 </li>
										<li orderType="price.desc">价格降序</li>
										<li orderType="sales.desc">销量降序</li>
										<li orderType="score.desc">评分降序</li>
										<li orderType="create_date.desc">日期降序</li>
								</ul>
							</div>
							<a href="javascript:;" class="asc  " orderType="price.asc">价格</a>
							<a href="javascript:;" class="desc " orderType="sales.desc">销量</a>
							<a href="javascript:;" class="desc " orderType="score.desc">评分</a>
							<input type="text" id="startPrice" name="startPrice" class="startPrice" value="" maxlength="16" title="价格过滤最低价格" onpaste="return false" />
							<label>-</label>
							<input type="text" id="endPrice" name="endPrice" class="endPrice" value="" maxlength="16" title="价格过滤最高价格" onpaste="return false" />
							<button type="submit">确 定</button>
						</div>
					</div>
					<div id="result" class="result grid clearfix">
							<ul>
									<li>
										<a href="/goods/content/47">
											<img src="/upload/image/blank.gif" data-original="http://image.demo.shopxx.net/4.0/201501/936b1088-e222-47d1-af6f-a4360a05ffde-thumbnail.jpg" />
											<div>
												<span title="苹果 iPhone 5s Case 保护套">苹果 iPhone 5s Case 保护套</span>
												<em title="原装品质，精雕细琢">原装品质，精雕细琢</em>
											</div>
										</a>
										<strong>
												￥0
													<del>￥288</del>
										</strong>
										<div class="action">
											<a href="javascript:;" class="addCart" productId="47">加入购物车</a>
											<a href="javascript:;" class="addFavorite" title="收藏" goodsId="47">&nbsp;</a>
											<a href="javascript:;" class="addCompare" title="对比" goodsId="47">&nbsp;</a>
										</div>
									</li>
									<li>
										<a href="/goods/content/34">
											<img src="/upload/image/blank.gif" data-original="http://image.demo.shopxx.net/4.0/201501/62142fec-eae0-4881-abc6-04ecb2afcbad-thumbnail.jpg" />
											<div>
												<span title="苹果 iPad mini3 MGY92CH">苹果 iPad mini3 MGY92CH</span>
												<em title="指纹识别，Retina显示屏">指纹识别，Retina显示屏</em>
											</div>
										</a>
										<strong>
												￥2888
													<del>￥3465</del>
										</strong>
										<div class="action">
											<a href="javascript:;" class="addCart" productId="34">加入购物车</a>
											<a href="javascript:;" class="addFavorite" title="收藏" goodsId="34">&nbsp;</a>
											<a href="javascript:;" class="addCompare" title="对比" goodsId="34">&nbsp;</a>
										</div>
									</li>
									<li>
										<a href="/goods/content/33">
											<img src="/upload/image/blank.gif" data-original="http://image.demo.shopxx.net/4.0/201501/5f7ffbaf-2e87-42c4-9039-0fe47fb630b2-thumbnail.jpg" />
											<div>
												<span title="苹果 iPad Air2 MH0W2CH">苹果 iPad Air2 MH0W2CH</span>
												<em title="超长电池使用时间">超长电池使用时间</em>
											</div>
										</a>
										<strong>
												￥3600
													<del>￥4320</del>
										</strong>
										<div class="action">
											<a href="javascript:;" class="addCart" productId="33">加入购物车</a>
											<a href="javascript:;" class="addFavorite" title="收藏" goodsId="33">&nbsp;</a>
											<a href="javascript:;" class="addCompare" title="对比" goodsId="33">&nbsp;</a>
										</div>
									</li>
							</ul>
					</div>
						<div class="pagination">
								<span class="firstPage">&nbsp;</span>
							
								<span class="previousPage">&nbsp;</span>
							
									<span class="currentPage">1</span>
									<a href="javascript: $.pageSkip(2);">2</a>
									<a href="javascript: $.pageSkip(3);">3</a>
							
								<a href="javascript: $.pageSkip(2);" class="nextPage">&nbsp;</a>
							
								<a href="javascript: $.pageSkip(3);" class="lastPage">&nbsp;</a>
						</div>
				</form>
			</div>
		</div>
	</div>
[#include "include/footer.ftl" /]
</body>
</html>