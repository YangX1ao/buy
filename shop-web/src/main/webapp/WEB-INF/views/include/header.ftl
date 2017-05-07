<div class="header">
	<div class="top">
		<div class="topNav">
			<ul class="left">
				<li>
					<span>您好，欢迎来到商HAI购</span>
						[#if LOGIN_USER?exists]
							<span id="headerName" class="headerName" style="display: inline;">${LOGIN_USER.username}</span>
						[/#if]
				</li>
				[#if LOGIN_USER?exists]
                    <li id="headerLogout" class="headerLogout" style="display: inline;">
                        <a href="${ctx}/user/logout">退出</a>
                    </li>
					[#else ]
                        <li id="headerLogin" class="headerLogin" >
                            <a href="javascript:loginRegister('login')">登录</a>|
                        </li>
                        <li id="headerRegister" class="headerRegister" >
                            <a href="javascript:loginRegister('register')">注册</a>|
                        </li>
				[/#if]
			</ul>
			<ul class="right">
				[@navigation_list position=0 ]
					[#if navigations?has_content ]
						[#list navigations as navigation]
							<li>
								<a href="${ctx}${navigation.url}" [#if navigation.isBlankTarget == 1 ] target="_blank"[/#if] >
									${navigation.name}
								</a>|
							</li>
						[/#list]
					[/#if]
				[/@navigation_list]


                    <li id="headerCart" class="headerCart">
					[#if LOGIN_USER?exists ]
                        <a href="${ctx}/cart/list">购物车</a>
                        (<em>0</em>)
					[#else]
                        <a href="javascript:loginRegister('login')">购物车</a>
                        (<em>0</em>)
					[/#if]
                    </li>
			</ul>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span3">
				<a href="${ctx}/index">
					<img src="${ctx}/upload/image/logo.gif" alt="尚HAI购" />
				</a>
			</div>
			<div class="span6">
				<div class="search">
					<form id="goodsSearchForm" action="${ctx}/goods/search" method="get">
						<input name="keyword" class="keyword" value="${resultList.baseDto.keyword}" autocomplete="off" x-webkit-speech="x-webkit-speech" x-webkit-grammar="builtin:search" maxlength="30" />
						<button type="submit">&nbsp;</button>
					</form>
				</div>
				<div class="hotSearch">
					热门搜索:
						[#--[@hot_search_keywords]
							[#if hotSearchKeywords?has_content]
								[#list hotSearchKeywords as keyword ]
									<a href="${ctx}/goods/search?keyword=${keyword}">${keyword}</a>
								[/#list]
							[/#if]
						[/@hot_search_keywords]--]
				</div>
			</div>
			<div class="span3">
				<div class="phone">
					<em>服务电话</em>
					800-8888888
				</div>
			</div>
		</div>
		<div class="row">
			<div class="span12">
				<dl class="mainNav">
					<dt>
						<a href="/product_category">所有商品分类</a>
					</dt>
					<dd>
					</dd>
					[#--获取顶级菜单--]
					[@navigation_list position=1 ]
						[#if navigations?has_content ]
							[#list navigations as navigation]
								<dd>
									<a href="${ctx}${navigation.url}" >
										${navigation.name}
									</a>
								</dd>
							[/#list]
						[/#if]
					[/@navigation_list]
				</dl>
			</div>
		</div>
	</div>
</div>
<script>
	$.post("${ctx}/cart/count", {}, function (data) {
		if (data.resultCode == 1) {
			var count = data.result;
			$("#headerCart").find('em').html(count);
		}
	});

	//登录
	function loginRegister(url){
		//获取当前的url
		var redirectUrl=window.location.href;
		if(redirectUrl.indexOf("/login")>-1 || redirectUrl.indexOf("/register")>-1){
		    redirectUrl='';
		}
        // encodeURIComponent转码 比encodeURI()强大
		window.location.href="${ctx}/"+url+"?redirectUrl="+encodeURIComponent(redirectUrl);
	}
</script>	