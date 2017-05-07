<!DOCTYPE html >
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>支付跳转中...</title>
</head>
<body style="background:#F3F3F4">
<br />
<br />
<div style="display: none">
    <form id='paysubmit' name='paysubmit' action='${gateway}' accept-charset='utf-8' method='post'>

        <input type='text' name='sign' value='${payRequestVo.sign}'/>

        <input type='text' name='body' value='${payRequestVo.body}'/>

        <input type='text' name='user_seller' value='${payRequestVo.userSeller}'/>

        <input type='text' name='total_fee' value='${payRequestVo.totalFee}'/>

        <input type='text' name='subject' value='${payRequestVo.subject}'/>

        <input type='text' name='notify_url' value='${payRequestVo.notifyUrl}'/>

        <input type='text' name='out_order_no' value='${payRequestVo.outOrderNo}'/>

        <input type='text' name='partner' value='${payRequestVo.partner}'/>

        <input type='text' name='return_url' value='${payRequestVo.returnUrl}'/>

        <input type='submit' value='支付进行中...' />
    </form>
</div>
<script type="text/javascript">
    document.forms['paysubmit'].submit();
</script>
</body>
</html>
