function init() {
    var $targetText = $('#targetText');
    var $doTextSegmentation = $('#doTextSegmentation');
    var $resultArea = $('#resultArea');

    $doTextSegmentation.click(function () {
        var text = $targetText.val();
        var segmentationMode = $("input:radio[name='segmentationMode']:checked").val();

        jsRoutes.controllers.Application.tokenize(text, segmentationMode).ajax({
            success: function (data) {
                $resultArea.empty();

                var $resultTableBody = $('<tbody/>');
                $resultTableBody.append('<tr><th>#</th><th>表記</th><th>品詞</th><th>基本形</th></tr>');

                for (var i = 0; i < data.tokens.length; i++) {
                    var token = data.tokens[i];
                    var $row = $('<tr/>');

                    var $col = $('<td/>');
                    $col.text(i + 1);
                    $row.append($col);

                    $col = $('<td/>');
                    $col.text(token.surfaceForm);
                    $row.append($col);

                    $col = $('<td/>');
                    $col.text(token.partOfSpeech);
                    $row.append($col);

                    $col = $('<td/>');
                    $col.text(token.baseForm);
                    $row.append($col);

                    $resultTableBody.append($row);
                }

                $resultArea.append($('<table/>').addClass('table table-striped').append($resultTableBody));
                $resultArea.append($('<div/>').text(data.time.toString() + " 秒"));
            }
        });
    });
}

$(init);