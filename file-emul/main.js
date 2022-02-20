$(document).ready(function () {
  $(this).bind("contextmenu", function (e) {
    e.preventDefault();
  });

  var board = [];

  var index = {};
  var rows = ['8', '7', '6', '5', '4', '3', '2', '1'];
  var files = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'];

  const heatStyleCount = 5;

  var currentPos = 0;

  const figures = {
    king: 'king',
    horse: 'horse',
    fire: 'fire'
  }
  var json = {
    "fire": [
      "a1",
      "a2"
    ],
    "king": "B3",
    "path": [
      "G4", "H6", "F7", "D6", "B5", "A3", "B1", "D2", "B3", "A5", "C6", "E7", "G6", "F8", "E6", "G7", "E8", "F6", "G4", "E3", "G4"
    ]
  }

  /** Cоздает доску
   */
  var initBoard = () => {
    board = [];
    tagged = {};
    $('.board').empty();

    var square = $("<div class='square'></div>");
    var addRow = (rowId) => {
      var row = $("<div class='row'></div>");
      board[rowId] = [];

      for (var i = 0; i < 8; i++) {
        var identifier = files[i] + rows[rowId];
        var newSquare = square
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

    for (var i = 0; i < 8; i++) {
      addRow(i);
    }
    $(".square").on("mousedown", clickHandler);
  };

  /** Добавляет указанную фигуру на доску.
   * @param {*} type тип фигуры.
   * @param {*} square клетка.
   */
  var initPiece = (type, square) => {
    console.log(type, square);
    $("." + square).append(`<div class='piece piece-${type}'></div>`);
    $("." + square).find(".txt").html("&nbsp;");
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

    json.path.forEach((step, number) => {
      if (number < currentPos)
        initPiece(figures.fire, step);
      else if (number === currentPos)
        initPiece(figures.horse, step);
      else if (step === json.king)
        initPiece(figures.king, step);
    });
  }

  function clickHandler(event) {
    var target = $(event.currentTarget);
    var id = target.find(".identifier").text();
    if (event.which == 1) {
      if (currentPos !== 0) {
        currentPos--;
        refresh();
      }
    } else {
      if (currentPos !== json.path.length - 1) {
        currentPos++;
        refresh();
      }
    }
  }

  refresh();
});
