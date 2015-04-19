function checkSignInForm() {

    var loginAlert = $('#login-alert');
    var loginAlertReserve = $('#login-alert-reserve');
    var loginError = $('#login-error');
    var loginErrorReserve = $('#login-error-reserve');
    var loginMessage = $('#login-message');
    var loginMessageReserve = $('#login-message-reserve');

    loginAlert.hide();
    loginError.hide();
    loginMessage.hide();
    loginErrorReserve.hide();
    loginAlertReserve.hide();
    loginMessageReserve.hide();

    var loginStr = $('#username').val();
    var passwordStr = $('#password').val();

    if (loginStr === "" || passwordStr === "") {
        loginAlert.show();
        loginError.hide();
        loginMessage.hide();
        return false;
    } else {
        return true;
    }
}

function checkSearchTrainForm() {

    var searchTrainAlert = $('#searchTrainAlert');
    var trainSearchingError = $('#trainSearchingErrorAlert');
    var trainNotFoundError = $('#trainNotFoundAlert');
    var trainTable = $('#search-train-table');
    var trainBtn = $('.centered-button');
    searchTrainAlert.hide();
    trainSearchingError.hide();
    trainNotFoundError.hide();
    trainTable.hide();
    trainBtn.hide();

    var stationFromName = $('#Station-From-Name').val();
    var stationToName = $('#Station-To-Name').val();
    var dateFrom = $('#search-train-date-from').val();

    if (stationFromName === "" || stationToName === "" || dateFrom === "") {
        searchTrainAlert.show();
        return false;
    }
    return true;
}

var stationNames = [];
var STEP_SIZE = 3;

function init_index_page() {

    var scheduleByStationCurrentPosition = 0;
    var searchTrainCurrentPosition = 0;
    var scheduleByStationMaxSize = 0;
    var searchTrainMaxSize = 0;

    $(document).ready(function ($) {
        // schedule by station ajax logic
        var scheduleByStationForm = $('#schedule-by-station-form');
        var scheduleByStationName = $('#schedule-by-station-name');
        scheduleByStationName.click(function () {
            scheduleByStationForm.removeClass("input-field-error");
        });
        scheduleByStationName.keypress(function () {
            scheduleByStationForm.removeClass("input-field-error");
        });
        var btnScheduleByStation = $('#btn-schedule-by-station');
        $('#schedule-by-station-ajax-previous').click(function (e) {
            e.preventDefault();
            scheduleByStationCurrentPosition -= STEP_SIZE;
            btnScheduleByStation.click();
        });
        $('#schedule-by-station-ajax-next').click(function (e) {
            e.preventDefault();
            scheduleByStationCurrentPosition += STEP_SIZE;
            btnScheduleByStation.click();
        });

        $(function () {
            var scheduleByStationName = $.session.get("scheduleByStationName");
            if (scheduleByStationName !== "" && scheduleByStationName !== null && scheduleByStationName !== undefined) {
                scheduleByStationForm.val(scheduleByStationName);
            }
        });

        btnScheduleByStation.click(function (e) {
                e.preventDefault();

                console.log("------------------------------------");
                console.log("scheduleByStationCurrentPosition: " + scheduleByStationCurrentPosition);
                console.log("scheduleByStationMaxSize: " + scheduleByStationMaxSize);
                console.log("------------------------------------");

                var stationName = scheduleByStationName.val();
                if (stationName === "") {
                    scheduleByStationForm.addClass("input-field-error");
                } else {

                    $.session.set("scheduleByStationName", stationName);

                    scheduleByStationForm.removeClass("input-field-error");
                    var preloader = $('#schedule-by-station-table-ajax-preloader');
                    var scheduleAjaxTable = $('#schedule-by-station-table-ajax');
                    var btnPrevious = $('#schedule-by-station-ajax-previous');
                    var btnNext = $('#schedule-by-station-ajax-next');
                    var empty = $('#schedule-by-station-table-ajax-empty');

                    scheduleAjaxTable.hide();
                    empty.hide();
                    preloader.show();

                    if (scheduleByStationMaxSize == 0) {
                        $.ajax({
                            url: "schedule_by_station/rest/maxSize",
                            data: {'schedule-by-station-name': stationName},
                            type: 'get',
                            success: function (data) {
                                scheduleByStationMaxSize = data;
                            }
                        });
                    }

                    $.ajax({
                        url: "schedule_by_station/rest",
                        data: {
                            'schedule-by-station-name': stationName,
                            'schedule-by-station-start-number': scheduleByStationCurrentPosition
                        },
                        type: 'get',
                        error: function () {
                            scheduleByStationForm.addClass("input-field-error");
                            preloader.hide();
                        },
                        success: function (data) {
                            var access = $('input[name=authorize-access]').val();
                            var context = $('input[name=context-path]').val();
                            console.log("access = " + access);
                            var json = JSON.parse(data);

                            preloader.hide();

                            if (json.length == 0) {
                                empty.html(' <p>Schedule for station "' + stationName + '" is empty</p>');
                                empty.show();
                            } else {

                                var tableBody = '';
                                $.each(json, function (index) {
                                    var trainNumber = json[index].trainNumber;
                                    tableBody += '<tr>';
                                    tableBody += '<td>' + trainNumber + '</td>';
                                    var departureDate = new Date(json[index].timeDeparture);
                                    tableBody += '<td>' + $.format.date(departureDate, 'yyyy-MM-dd HH:mm') + '</td>';
                                    var stationList = json[index].stationList;
                                    var stationListStr = '';
                                    for (var i = 0; i < stationList.length; i++) {
                                        stationListStr += stationList[i].name;
                                        if (i < stationList.length - 1) {
                                            stationListStr += ', ';
                                        }
                                    }
                                    tableBody += '<td>' + stationListStr + '</td>';
                                    var btnBuyTicket;
                                    if (access === "authenticated") {
                                        btnBuyTicket = '<form action="' + context + '/buy_ticket/step_1" method="post">' +
                                        '<input type="hidden" name="trainNumber" value="' + trainNumber + '">' +
                                        '<input type="hidden" name="dispatchStation" value="' + stationName + '">' +
                                        '<input type="hidden" name="departureDate" value="' +
                                        $.format.date(departureDate, 'yyyy-MM-dd') + '">' +
                                        '<input type="hidden" name="departureTime" value="' +
                                        $.format.date(departureDate, 'HH:mm') + '">' +
                                        '<button type="submit" class="btn btn-block btn-success btn-buy">Buy</button></form>';
                                    } else {
                                        btnBuyTicket = '<a href="' + context +
                                        '/login?msg=buy" class="btn disabled btn-block btn-buy btn-buy-disabled">Buy</a>';
                                    }
                                    tableBody += '<td>' + btnBuyTicket + '</td>';
                                    tableBody += '</tr>';
                                });
                                scheduleAjaxTable.html(
                                    '<table class="body-table table table-bordered">' +
                                    '<thead><tr><th>Train number</th><th>Departure</th><th>Stations</th>' +
                                    '<th>Buy ticket</th>' +
                                    '</tr></thead>' +
                                    '<tbody>' + tableBody + '</tbody>' +
                                    '</table>'
                                );

                                scheduleAjaxTable.show();
                                if (scheduleByStationCurrentPosition >= STEP_SIZE) {
                                    btnPrevious.show();
                                } else {
                                    btnPrevious.hide();
                                }
                                if (scheduleByStationCurrentPosition + STEP_SIZE < scheduleByStationMaxSize) {
                                    btnNext.show();
                                } else {
                                    btnNext.hide();
                                }
                            }
                        }
                    });
                }
            }
        );
        // search train ajax logic
        var searchTrainForm = $('#search-train-form');
        var searchTrainStationFromName = $('#search-train-station-from-name');
        var searchTrainStationToName = $('#search-train-station-to-name');
        var searchTrainDateFrom = $('#search-train-date-from');
        searchTrainStationFromName.click(function () {
            searchTrainForm.removeClass("input-field-error");
        });
        searchTrainStationFromName.keypress(function () {
            searchTrainForm.removeClass("input-field-error");
        });
        searchTrainStationToName.click(function () {
            searchTrainForm.removeClass("input-field-error");
        });
        searchTrainStationToName.keypress(function () {
            searchTrainForm.removeClass("input-field-error");
        });
        searchTrainDateFrom.click(function () {
            searchTrainForm.removeClass("input-field-error");
        });
        searchTrainDateFrom.keypress(function () {
            searchTrainForm.removeClass("input-field-error");
        });
        var btnSearchTrain = $('#btn-search-train');
        $('#search-train-ajax-previous').click(function (e) {
            e.preventDefault();
            searchTrainCurrentPosition -= STEP_SIZE;
            console.log('searchTrainCurrentPosition: ' + searchTrainCurrentPosition);
            btnSearchTrain.click();
        });
        $('#search-train-ajax-next').click(function (e) {
            e.preventDefault();
            searchTrainCurrentPosition += STEP_SIZE;
            console.log('searchTrainCurrentPosition: ' + searchTrainCurrentPosition);
            btnSearchTrain.click();
        });

        $(function () {
            var stationFromName = $.session.get("stationFromName");
            if (stationFromName !== "" && stationFromName !== null
                && stationFromName !== undefined) {
                searchTrainStationFromName.val(stationFromName);
            }
            var stationToName = $.session.get("stationToName");
            if (stationToName !== "" && stationToName !== null
                && stationToName !== undefined) {
                searchTrainStationToName.val(stationToName);
            }
            var dateFrom = $.session.get("dateFrom");
            if (dateFrom !== "" && dateFrom !== null
                && dateFrom !== undefined) {
                searchTrainDateFrom.val($.format.date(dateFrom, 'yyyy-MM-dd'));
            }
        });

        btnSearchTrain.click(function (e) {
                e.preventDefault();
                var stationFromName = searchTrainStationFromName.val();
                var stationToName = searchTrainStationToName.val();
                var dateFrom = searchTrainDateFrom.val();
                if (stationFromName === '' || stationToName === '' || dateFrom === '') {
                    searchTrainForm.addClass("input-field-error");
                } else {

                    $.session.set("stationFromName", stationFromName);
                    $.session.set("stationToName", stationToName);
                    $.session.set("dateFrom", $.format.date(dateFrom, 'yyyy-MM-dd'));

                    searchTrainForm.removeClass("input-field-error");

                    var preloader = $('#search-train-table-ajax-preloader');
                    var searchTrainAjaxTable = $('#search-train-table-ajax');
                    var btnPrevious = $('#search-train-ajax-previous');
                    var btnNext = $('#search-train-ajax-next');
                    var empty = $('#search-train-table-ajax-empty');

                    searchTrainAjaxTable.hide();
                    empty.hide();
                    preloader.show();

                    if (searchTrainMaxSize == 0) {
                        $.ajax({
                            url: "search_train/rest/maxSize",
                            data: {
                                'search-train-from-name': stationFromName,
                                'search-train-to-name': stationToName,
                                'search-train-date-from': dateFrom
                            },
                            type: 'get',
                            error: function () {
                                searchTrainForm.addClass("input-field-error");
                                preloader.hide();
                            },
                            success: function (data) {
                                searchTrainMaxSize = data;
                            }
                        });
                    }

                    $.ajax({
                        url: "search_train/rest",
                        data: {
                            'search-train-from-name': stationFromName,
                            'search-train-to-name': stationToName,
                            'search-train-date-from': dateFrom,
                            'search-train-start-number': searchTrainCurrentPosition
                        },
                        type: 'get',
                        error: function () {
                            console.log('Error with: station from: ' + stationFromName + ', station to: ' + stationToName
                            + ', date from: ' + dateFrom);
                            searchTrainForm.addClass("input-field-error");
                            preloader.hide();
                        },
                        success: function (data) {

                            console.log(data);

                            var access = $('input[name=authorize-access]').val();
                            var context = $('input[name=context-path]').val();
                            console.log("access = " + access);
                            var json = JSON.parse(data);

                            preloader.hide();

                            if (json.length == 0) {
                                empty.html(' <p>Schedule for station "' + stationFromName +
                                '" to station "' + stationToName + 'on date ' + dateFrom + '"is empty</p>');
                                empty.show();
                            } else {

                                var tableBody = '';
                                $.each(json, function (index) {
                                    var trainNumber = json[index].trainNumber;
                                    tableBody += '<tr>';
                                    tableBody += '<td>' + trainNumber + '</td>';
                                    var departureDate = new Date(json[index].timeDeparture);
                                    tableBody += '<td>' + $.format.date(departureDate, 'yyyy-MM-dd HH:mm') + '</td>';
                                    var arrivalDate = new Date(json[index].timeArrival);
                                    tableBody += '<td>' + $.format.date(arrivalDate, 'yyyy-MM-dd HH:mm') + '</td>';

                                    var btnBuyTicket;
                                    if (access === "authenticated") {
                                        btnBuyTicket = '<form action="' + context + '/buy_ticket/step_1" method="post">' +
                                        '<input type="hidden" name="trainNumber" value="' + trainNumber + '">' +
                                        '<input type="hidden" name="dispatchStation" value="' + stationFromName + '">' +
                                        '<input type="hidden" name="departureDate" value="' +
                                        $.format.date(departureDate, 'yyyy-MM-dd') + '">' +
                                        '<input type="hidden" name="departureTime" value="' +
                                        $.format.date(departureDate, 'HH:mm') + '">' +
                                        '<button type="submit" class="btn btn-block btn-success btn-buy">Buy</button></form>';
                                    } else {
                                        btnBuyTicket = '<a href="' + context +
                                        '/login?msg=buy" class="btn disabled btn-block btn-buy btn-buy-disabled">Buy</a>';
                                    }
                                    tableBody += '<td>' + btnBuyTicket + '</td>';
                                    tableBody += '</tr>';
                                });
                                searchTrainAjaxTable.html(
                                    '<table class="body-table table table-bordered">' +
                                    '<thead><tr><th>Train number</th>' +
                                    '<th>' + stationFromName + ' departure time</th>' +
                                    '<th>' + stationToName + ' arrival time</th>' +
                                    '<th>Buy ticket</th>' +
                                    '</tr></thead>' +
                                    '<tbody>' + tableBody + '</tbody>' +
                                    '</table>'
                                );

                                searchTrainAjaxTable.show();
                                if (searchTrainCurrentPosition >= STEP_SIZE) {
                                    btnPrevious.show();
                                } else {
                                    btnPrevious.hide();
                                }
                                if (searchTrainCurrentPosition + STEP_SIZE < searchTrainMaxSize) {
                                    btnNext.show();
                                } else {
                                    btnNext.hide();
                                }
                            }
                        }
                    });
                }
            }
        );
        // load all station names
        $.get('stations', function (data) {
            stationNames = JSON.parse(data);
        });
        scheduleByStationName.typeahead({
            source: function () {
                return stationNames;
            },
            items: 5,
            minLength: 0
        });
        searchTrainStationFromName.typeahead({
            source: function () {
                return stationNames;
            },
            items: 5,
            minLength: 0
        });
        searchTrainStationToName.typeahead({
            source: function () {
                return stationNames;
            },
            items: 5,
            minLength: 0
        });
        $(function () {
            $('#search-train-date-from').datepicker({
                startDate: new Date(),
                todayHighlight: true,
                format: "yyyy-mm-dd"
            });
        });
    });
}

function init_buy_ticket_page() {
    $(document).ready(function ($) {
        $('#buy-ticket-date-from').datepicker({
            orientation: "bottom",
            startDate: new Date(),
            todayHighlight: true,
            format: "yyyy-mm-dd"
        });
        $('#buy-ticket-time-from').timepicker({
            showSeconds: false,
            showMeridian: false,
            minuteStep: 1
        });
        $('#buy-ticket-birthdate-from').datepicker({
            orientation: "bottom",
            endDate: new Date(),
            startView: "decade",
            todayHighlight: true,
            format: "yyyy-mm-dd"
        });
        $('#buy-ticket-expires-from').datepicker({
            orientation: "bottom",
            startDate: new Date(),
            startView: "months",
            minViewMode: "months",
            todayHighlight: true,
            format: "yyyy-mm"
        });
        var buyTicketFromError = $('.buy-ticket-form-error');
        buyTicketFromError.click(function () {
            $(this).removeClass("buy-ticket-form-error");
            $(this).find("p").remove();
        });
        buyTicketFromError.keypress(function () {
            $(this).removeClass("buy-ticket-form-error");
            $(this).find("p").remove();
        });
    });
}
