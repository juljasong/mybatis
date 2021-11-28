/**
 * Search
 */
const searchInput = document.getElementById('searchInput');
searchInput.addEventListener('input', () => {
  console.log(searchInput.value);
});

function getUrls() {
  $('#result-list').empty();
  $('#member-list-button').removeClass('active');
  $('#order-list-button').removeClass('active');
  $('#url-list-button').addClass('active');

  let list = document.getElementById('result-list');

  $.ajax({
    url: '/admin/urls',
    type: 'GET',
    dataType: 'json',
  }).done((data) => {
    console.log(data);
    for (i in data) {
      list.innerHTML += `<li class="list-group-item">${data[i].id} | ${data[i].memberId} | ${data[i].name} | ${data[i].url}</li>`;
    }
  });
}

function getMembers() {
  $('#result-list').empty();
  $('#url-list-button').removeClass('active');
  $('#order-list-button').removeClass('active');
  $('#member-list-button').addClass('active');

  let list = document.getElementById('result-list');

  $.ajax({
    url: '/admin/members',
    type: 'GET',
    dataType: 'json',
  }).done((data) => {
    for (i in data) {
      list.innerHTML += `<li class="list-group-item"> ${data[i].id} | ${data[i].email} | ${data[i].name} | ${data[i].createDate}</li>`;
    }
  });
}

function getOrders() {
  $('#result-list').empty();
  $('#url-list-button').removeClass('active');
  $('#member-list-button').removeClass('active');
  $('#order-list-button').addClass('active');

  let list = document.getElementById('result-list');

  $.ajax({
    url: '/admin/orders',
    type: 'GET',
    dataType: 'json',
  }).done((data) => {
    for (i in data) {
      list.innerHTML += `<li class="list-group-item">${data[i].id} | ${data[i].memberId} | ${data[i].startDate} | ${data[i].endDate}</li>`;
    }
  });
}
