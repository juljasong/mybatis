const inputSearchText = document.getElementById('inputSearchText');
const active = document.getElementsByClassName('active')[0].innerHTML;

inputSearchText.addEventListener('input', () => {
  let searchText = inputSearchText.value;
  let searchTable = document.getElementById('search-tbody');
  $('#search-tbody').empty();

  if (searchText == '') {
    $('#result-table').show();
    $('#search-result-table').hide();
  } else {
    $('#search-result-table').show();
    $('#result-table').hide();

    if (active == 'URLS') {
      $.ajax({
        url: '/admin/urls/' + searchText,
        type: 'get',
      }).done((data) => {
        for (i in data) {
          searchTable.innerHTML += `<tr><th scope="memberId">${data[i].memberId}</th><td onclick="location.href='/admin/url/${data[i].id}'">${data[i].name}</td><td>${data[i].url}</td></tr>`;
        }
      });
    } else if (active == 'MEMBERS') {
      $.ajax({
        url: '/admin/members/' + searchText,
        type: 'get',
      }).done((data) => {
        for (i in data) {
          let date = getFormatDate(`${data[i].createDate}`);
          searchTable.innerHTML += `<tr><th scope="id">${data[i].id}</th><td onclick="location.href='/admin/member/${data[i].id}'">${data[i].email}</td><td>${data[i].name}</td><td>${date}</td><td>${data[i].provider}</td></tr>`;
        }
      });
    } else if (active == 'ORDERS') {
      $.ajax({
        url: '/admin/orders/' + searchText,
        type: 'get',
      }).done((data) => {
        for (i in data) {
          let startDate = getFormatDate(`${data[i].startDate}`);
          let endDate = getFormatDate(`${data[i].endDate}`);
          searchTable.innerHTML += `<tr><th scope="id" onclick="location.href='/admin/order/${data[i].id}'">${data[i].id}</th><td>${data[i].memberId}</td><td>${data[i].productId}</td><td>${startDate}</td><td>${endDate}</td></tr>`;
        }
      });
    }
  }
});

function getFormatDate(str) {
  let date = new Date(str);

  const year = date.getFullYear(); //yyyy
  const month = ('0' + (date.getMonth() + 1)).slice(-2);
  const day = ('0' + date.getDate()).slice(-2);

  const hours = ('0' + date.getHours()).slice(-2);
  const minutes = ('0' + date.getMinutes()).slice(-2);
  const seconds = ('0' + date.getSeconds()).slice(-2);

  let timeString = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;

  return timeString;
}

function deleteUrl(id) {
  $.ajax({
    url: '/admin/url/delete/',
    type: 'GET',
    data: { id: id },
  }).done(() => {
    alert('ok');
    location.href = '/admin/urls';
  });
}
