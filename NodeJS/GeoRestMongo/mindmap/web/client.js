/**
 * New node file
 */

var eventBus = new vertx.EventBus(window.location.protocol + '//'
		+ window.location.hostname + ':' + window.location.port + '/eventbus');

eventBus.onopen = function() {
	console.log("Event bus connected!!!!!");

	var renderListItem = function(mindMap) {
		var li = $('<li>');
		var openMindMap = function() {
			new MindMapEditor(mindMap, eventBus);
			return false;
		};
		var deleteMindMap = function() {
			eventBus.send('mindMaps.delete', {
				id : mindMap._id
			}, function() {
				li.remove();
			});
			return false;
		};

		$('<a>').text(mindMap.name).attr('href', '#').on('click', openMindMap)
				.appendTo(li);
		$('<button>').text('Delete').on('click', deleteMindMap).appendTo(li);
		li.appendTo('.mind-maps');
	};

	$('.create-form').submit(function() {

		var nameInput = $('[name=name]', this);
		console.log("nameInput : " + nameInput.val());
		eventBus.send('mindMaps.save', {
			name : nameInput.val()
		}, function(result) {
			renderListItem(result);
			nameInput.val('');
		});
		return false;
	});

	eventBus.send('mindMaps.list', {}, function(res) {
		console.log(res);
		$.each(res.mindMaps, function() {
			renderListItem(this);
		});
	})

};
