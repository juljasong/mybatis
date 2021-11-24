function copy() {
  const text = document.createElement('textarea');
  document.body.appendChild(text);
  text.value = document.getElementById('copy_url').innerText;
  text.select();
  document.execCommand('copy');
  document.body.removeChild(text);

  alert('Copied!');
}

function updateForm(no) {
  $.ajax({
    url: '/url/update',
    type: 'GET',
    data: { id: no },
  })
    .done((data) => {
      $('#updateName').val(data.name);
      $('#updateUrl').val(data.url);
      $('#updateDescription').val(data.description);
      $('#updateDate').val(data.expirationDate);
      if (data.isPublic == 1) {
        $('#updateIsPublic').attr('checked', true);
      }
      $('#updateId').val(data.id);
      $('#delete-btn').attr(
        'onclick',
        `location.href="/url/delete/${data.id}"`
      );
    })
    .fail(() => {})
    .always(() => {});
}

// iamport
var IMP = window.IMP;
IMP.init('imp71830241');

function joinPlus(no) {
  // IMP.request_pay(param, callback)
  $.ajax({
    url: '/payments/plus',
    type: 'GET',
    data: { productId: no },
  }).done((data) => {
    console.log(data);
    IMP.request_pay(
      {
        // param
        pg: 'danal_tpay',
        pay_method: 'card',
        merchant_uid: data.merchantUid,
        name: data.orderName,
        amount: data.amount,
        buyer_email: data.buyerEmail,
        buyer_name: data.buyerName,
      },
      function (rsp) {
        // callback
        if (rsp.success) {
          //[1] 서버단에서 결제정보 조회를 위해 jQuery ajax로 imp_uid 전달하기
          console.log(rsp);
          $.ajax({
            url: '/payments/complete', //cross-domain error가 발생하지 않도록 주의해주세요
            type: 'POST',
            dataType: 'json',
            data: {
              impUid: rsp.imp_uid,
              payMethod: rsp.pay_method,
              merchantUid: rsp.merchant_uid,
              applyNum: rsp.apply_num,
              cardName: rsp.card_name,
              cardNum: rsp.card_number,
              quota: rsp.card_quota,
              amount: rsp.paid_amount,
              paidAt: rsp.paid_at,
              productId: no,
            },
          }).done(function (data) {
            //[2] 서버에서 REST API로 결제정보확인 및 서비스루틴이 정상적인 경우
            if (rsp.statusCode !== 200) {
              var msg = '결제가 완료되었습니다.';
              msg += '\n고유ID : ' + data.impUid;
              msg += '\n상점 거래ID : ' + data.merchantUid;
              msg += '\n결제 금액 : ' + data.amount;
              msg += '\n카드 승인번호 : ' + data.applyNum;

              alert(msg);
            } else {
              var msg = '결제에 실패하였습니다.';
              // msg += '\n에러내용 : ' + rsp;

              alert(msg);
            }
          });
        }
      }
    );
  });
}
