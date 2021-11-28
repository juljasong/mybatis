/**
 * Search
 */
const inputSearchText = document.getElementById('inputSearchText');

function getUrls() {
  $('#result-table').empty();
  $('#search-result-table').empty();
  $('#member-list-button').removeClass('active');
  $('#order-list-button').removeClass('active');
  $('#url-list-button').addClass('active');

  let table = document.getElementById('result-table');
  let searchTable = document.getElementById('search-result-table');

  $.ajax({
    url: '/admin/urls',
    type: 'GET',
    dataType: 'json',
  })
    .done((data) => {
      table.innerHTML +=
        '<thead>' +
        '<th scope="col">#</th>' +
        '<th scope="col">NAME</th>' +
        '<th scope="col">URL</th>' +
        '</thead>' +
        '<tbody>';
      for (i in data) {
        table.innerHTML += `<tr><th scope="memberId">${data[i].memberId}</th><td>${data[i].name}</td><td>${data[i].url}</td>`;
      }
      table.innerHTML += '</tbody>';
    })
    .always((data) => {
      inputSearchText.addEventListener('input', () => {
        let searchText = inputSearchText.value;
        searchTable.innerHTML = '';

        if (searchText == '') {
          $('#result-table').show();
          $('#search-result-table').hide();
        } else {
          $('#search-result-table').show();
          $('#result-table').hide();

          $.ajax({
            url: '/admin/urls/' + searchText,
            type: 'get',
          }).done((data) => {
            searchTable.innerHTML +=
              '<thead>' +
              '<th scope="col">#</th>' +
              '<th scope="col">NAME</th>' +
              '<th scope="col">URL</th>' +
              '</thead>' +
              '<tbody>';
            for (i in data) {
              searchTable.innerHTML += `<tr><th scope="memberId">${data[i].memberId}</th><td>${data[i].name}</td><td>${data[i].url}</td>`;
            }

            searchTable.innerHTML += '</tbody>';
          });
        }
      });
    });
}

function getMembers() {
  $('#result-table').empty();
  $('#url-list-button').removeClass('active');
  $('#order-list-button').removeClass('active');
  $('#member-list-button').addClass('active');

  let list = document.getElementById('result-table');

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
  $('#result-table').empty();
  $('#url-list-button').removeClass('active');
  $('#member-list-button').removeClass('active');
  $('#order-list-button').addClass('active');

  let list = document.getElementById('result-table');

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
