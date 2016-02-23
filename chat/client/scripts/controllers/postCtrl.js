angular.module('app').controller('postCtrl', function ($scope, $reactive) {

	$reactive(this).attach($scope);

	this.helpers({
	    posts() {
	    	return Posts.find({});
	    }
	});

	this.doPost = function (post) {
		Posts.insert(post);
		/*for (var i = 0; i < post.length; i++) {
			Posts.remove({_id: post[i]._id});
		}*/
	};

});
