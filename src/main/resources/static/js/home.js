$('.url-list').on('click', (e) => {
  const id = e.target.id;
  const form = $(`#form_${id}`).serializeArray();
  console.log(form);

  // 폼에 입력
  $('#update_name').val(`${form[0].value}`);
  $('#update_url').val(`${form[1].value}`);
  $('#update_description').val(`${form[2].value}`);
  $('#update_expirationDate').val(`${form[3].value}`);
  if (form[4].value == 1) {
    $('#update_isPublic').attr('checked', true);
  }
  $('#update_id').val(`${form[5].value}`);
  $('#btnDelete').attr(
    'onclick',
    `location.href="/url/delete/${form[5].value}"`
  );
});

function copy() {
  const t = document.createElement('textarea');
  document.body.appendChild(t);
  t.value = document.getElementById('copy_url').innerText;
  t.select();
  document.execCommand('copy');
  document.body.removeChild(t);

  alert('Copied!');
}
