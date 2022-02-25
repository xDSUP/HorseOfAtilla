let json = {
  "fire": [
    "A1",
    "A2"
  ],
  "king": "G2",
  "path": [
    "D7", "F8", "G6", "H4", "G2", "E1", "F3", "E5", "D7"
  ],
  "horseStartPostiton": "D7"
}

$(document).ready(function () {
  $(this).bind("contextmenu", function (e) {
    e.preventDefault();
  });

  let board = [];
  let index = {};
  let rows = ['8', '7', '6', '5', '4', '3', '2', '1'];
  let files = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'];

  const heatStyleCount = 5;
  let currentPos = 0;

  const figures = Object.freeze({
    king: 'king',
    horse: 'horse',
    fire: 'fire'
  });

  let activeFigure = figures.horse;



  /** Cоздает доску
   */
  let initBoard = () => {
    board = [];
    tagged = {};
    $('.board').empty();

    var square = $("<div class='square'></div>");
    var addRow = (rowId) => {
      var row = $("<div class='row'></div>");
      board[rowId] = [];

      for (let i = 0; i < 8; i++) {
        let identifier = files[i] + rows[rowId];
        let newSquare = square
          .clone()
          .addClass(identifier)
          .append(`<span class='identifier'>${identifier}</span>`)
          .append(`<span class='txt'>-</span>`);
        row.append(newSquare);
        board[rowId][i] = identifier;
        index[identifier] = [rowId, i];
      }
      $('.board').append(row);
    }

    for (let i = 0; i < 8; i++) {
      addRow(i);
    }
    $(".square").on("mousedown", clickHandler);
  };

  /** Добавляет указанную фигуру на доску.
   * @param {*} type тип фигуры.
   * @param {*} square клетка.
   */
  let initPiece = (type, square) => {
    console.log(type, square);
    let selectedSquare = $("." + square);
    selectedSquare.append(`<div class='piece piece-${type}'></div>`);
    selectedSquare.find(".txt").html("&nbsp;");
  };

  /** Закрашивает клетку и пишет в нее цифру
   * @param {*} step Клетка
   * @param {*} val Цифра
   */
  var tagSquare = (step, val) => {
    if (!$("." + step).hasClass("heat")) {
      $("." + step).addClass("heat");
      $("." + step).find(".txt").text(val);
      let heatVal = val % heatStyleCount;
      $("." + step).addClass("heat-" + heatVal);
    }
  }

  /** Рисует путь
   * @param {*} path массив - путь фигуры. 
   */
  var renderPath = (path) => {
    let val = 0;
    path.forEach((step) => {
      tagSquare(step, val);
      val++;
    });
  }

  var refresh = () => {
    initBoard();
    renderPath(json.path);
    json.fire.forEach((position) => {
      initPiece(figures.fire, position);
    });

    if(currentPos === 0){
      initPiece(figures.horse, json.horseStartPostiton);
      initPiece(figures.king, json.king);
    }
    else{
      json.path.forEach((step, number) => {
        if (number < currentPos)
          initPiece(figures.fire, step);
        else if (number === currentPos)
          initPiece(figures.horse, step);
        else if (step === json.king)
          initPiece(figures.king, step);
      });
    }
  }

  let backStep = () => {
    if (currentPos !== 0) {
      currentPos--;
      refresh();
    }
  }

  let nextStep = () => {
    if (currentPos !== json.path.length - 1) {
      currentPos++;
      refresh();
    }
  }

  function clickHandler(event) {
    let target = $(event.currentTarget);
    let id = target.find(".identifier").text();
    if (event.which === 1) {
      switch (activeFigure) {
        case figures.horse:
          currentPos = 0;
          json.horseStartPostiton = id;
          break;
        case figures.king:
          json.king = id;
          break;
        case figures.fire:
          json.fire.push(id);
          break;
      }
      refresh();

    } else {
      json.fire = json.fire.filter(function(elem){
        return elem !== id;
      })
      refresh();
    }
    refresh();
  }

  $("#backStep").click(backStep);
  $("#nextStep").click(nextStep);
  $("#getSolve").click(() => {
    $.ajax("http://localhost:8080/atilla", {
      method: 'post',
      contentType: 'application/json',
      dataType: 'json',
      data: JSON.stringify({fire: json.fire, king: json.king, horse: json.horseStartPostiton}),
      success: function(data){
        json.path = data.path;
        refresh();
      },
      statusCode: {
        500: function(data) {
          let str = `${data.responseJSON.timestamp}<br><br>${data.status} ${data.responseJSON.error}<br><br>${data.responseJSON.message}<br><br>${data.responseJSON.trace}`;
          $(".state").html(str);
        }
      }
    })
  });
  $("#fire").click(() => activeFigure = figures.fire);
  $("#horse").click(() => activeFigure = figures.horse);
  $("#king").click(() => activeFigure = figures.king);

  refresh();
});
