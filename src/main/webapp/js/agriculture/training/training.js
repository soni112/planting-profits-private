/**
 * Created by abhishek on 29/9/16.
 */

$(function () {
    $('#header-user-nav').hide();
})


var videoPlayer = document.getElementById("videoPlayer");

function switchVideo(currentReference){

    // currentReference.preventDefault();

    var $this = $(currentReference);
    var videoLink = $this.attr('data-link');

    videoPlayer.pause();

    $('#videoPlayer').attr('src', videoLink);

    videoPlayer.play();

}
