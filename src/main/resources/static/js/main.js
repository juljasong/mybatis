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
      console.log(data);
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
