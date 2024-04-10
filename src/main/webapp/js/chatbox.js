var inboxType = 0;
var imgInp = document.getElementById("imgInp");
var groupProfilePic = document.getElementById("groupProfilePic");
var currentIndex = 0;
var images = [];
var stories = [];

function seeProfile(){
    $( ".left" ).css( "z-index", "1" );
    $( ".leftUpdate" ).css( "z-index", "1" );
    $( ".leftSide" ).css( "z-index", "3" );
    $( ".right" ).css( "z-index", "2" );
}

function seeChatList(test){
    $( ".left" ).css( "z-index", "3" );
    $( ".chatlist" ).css( "z-index", "2" );
    $( ".storiesList" ).css( "z-index", "1" );
    $( ".search-chat" ).css( "z-index", "3" );
    $( ".leftSide" ).css( "z-index", "1" );
    $( ".leftYourStory" ).css( "z-index", "1" );
    $( ".right" ).css( "z-index", "2" );
    closeInbox();
}

function seeStories(){
    $( ".storiesList" ).css( "z-index", "3" );
    $( ".search-chat" ).css( "z-index", "1" );
    $( ".chatlist" ).css( "z-index", "1" );
    $( ".right" ).css( "z-index", "1" );
}

function seeYourStories(){
    $( ".leftYourStory" ).css( "z-index", "3" );
    $( ".left" ).css( "z-index", "1" );
    $( ".leftSide" ).css( "z-index", "1" );
    $( ".right" ).css( "z-index", "1" );
}

function seeProfileUpdate(){
    $( ".left" ).css( "z-index", "1" );
    $( ".leftUpdate" ).css( "z-index", "3" );
    $( ".leftSide" ).css( "z-index", "1" );
    $( ".right" ).css( "z-index", "2" );
}

function openFileInput() {
    // Trigger a click on the hidden file input
    document.getElementById('msgFile').click();
}

function openProfileInput(){
    document.getElementById('newProfilePic').click();
}

/**
 * see the menu in the header (the triple dots on the left)
 */
function seeHeaderMenu() {
    $(".drop-menu").css("visibility", "visible");

    // Add click event listener to the document to close the dropdown when clicking outside
    $(document).on("click", function (event) {
        var dropdown = $(".drop-menu");
        var icon = $(".header-menu");

        // Check if the clicked element is not part of the dropdown or the header-menu icon
        if (!dropdown.is(event.target) && dropdown.has(event.target).length === 0 && !icon.is(event.target)) {
            // Close the dropdown
            dropdown.css("visibility", "hidden");
        }
    });
}

// Prevent clicks inside the dropdown from closing it
$(".drop-menu").on("click", function (event) {
    event.stopPropagation();
});

// Example: If you want to close the dropdown when a menu item is clicked
$(".drop-menu li").on("click", function () {
    $(".drop-menu").css("visibility", "hidden");
});

/**
 * see the menu in the header (the triple dots on the left)
 */
function seeChatMenu() {
    $(".drop-menu-chat").css("visibility", "visible");

    // Add click event listener to the document to close the dropdown when clicking outside
    $(document).on("click", function (event) {
        var dropdown = $(".drop-menu-chat");
        var icon = $(".chat-menu");

        if(inboxType === 1){
            dropdown.html("<ul>" +
                "<a href='controller?action=block_user'><li>block user</li></a>" +
                "<li onclick='openForm()'>report user</li>" +
                "<a href='controller?action=leave_chat'><li>leave chat</li></a>" +
                "</ul>");
        }
        else{
            dropdown.html("<ul>" +
                "<li onclick='openGroupPage()'>invite member</li>" +
                "<li>edit group</li>" +
                "<li>leave group</li>" +
                "</ul>");
        }

        // Check if the clicked element is not part of the dropdown or the header-menu icon
        if (!dropdown.is(event.target) && dropdown.has(event.target).length === 0 && !icon.is(event.target)) {
            // Close the dropdown
            dropdown.css("visibility", "hidden");
        }
    });
}

// Prevent clicks inside the dropdown from closing it
$(".drop-menu-chat").on("click", function (event) {
    event.stopPropagation();
});

// Example: If you want to close the dropdown when a menu item is clicked
$(".drop-menu-chat li").on("click", function () {
    $(".drop-menu-chat").css("visibility", "hidden");
});

imgInp.onchange = () => {
    const [file] = imgInp.files
    if (file) {
        groupProfilePic.src = URL.createObjectURL(file)
    }
}

function seeMessage(type){
    $( ".left" ).css( "z-index", "2" );
    $( ".leftSide" ).css( "z-index", "1" );
    $( ".right" ).css( "z-index", "3" );

    inboxType = type;

    visibleMessage();
}

function visibleMessage(){
    $("#chat-hide").css("visibility", "visible");
    $(".chatbox").css("visibility", "visible");
    $(".chatbox-input").css("visibility", "visible");
}

function checkImage(clickedDiv) {
    let imageElement = clickedDiv.querySelector('img');
    let imageUrl = imageElement.src;

    // Remove the protocol part from the URL
    let relativePath = imageUrl.replace(/^(https?:\/\/[^/]+)?/, '');

    document.querySelector('.zoom img').src = relativePath;
    document.querySelector('.zoom').style.display = 'flex';
}

function openForm(){
    document.querySelector('.report-page').style.display = 'flex';
}

function openCreateGroupPage(){
    document.querySelector('.create-group-page').style.display = 'flex';
}

function closeReport() {
    document.querySelector('.report-page').style.display = 'none';
}

function openStoryView(imagesList){
    const storyImage = document.getElementById('storyImage');
    images = imagesList;
    storyImage.src = images[0];
    viewStory(0);
    let bars = document.querySelectorAll(".bars");
    bars[0].innerHTML += "<div class='gray-bar'></div>";
    bars[1].innerHTML += "<div class='loading-bar'></div>";

    for(let i = 1; i < images.length; i++){
        bars[0].innerHTML += "<div class='gray-bar'></div>";
        bars[1].innerHTML += "<div class='loading-bar' style='visibility: hidden;'></div>";
    }
    document.querySelector('.storyview').style.display = 'flex';
}
function openMyStoryView(imagesList){
    const storyImage = document.getElementById('storyImage');
    images = imagesList;
    storyImage.src = images[0];
    let bars = document.querySelectorAll(".bars");
    bars[0].innerHTML += "<div class='gray-bar'></div>";
    bars[1].innerHTML += "<div class='loading-bar'></div>";

    for(let i = 1; i < images.length; i++){
        bars[0].innerHTML += "<div class='gray-bar'></div>";
        bars[1].innerHTML += "<div class='loading-bar' style='visibility: hidden;'></div>";
    }
    document.querySelector('.storyview').style.display = 'flex';
}

function closeStoryView(){
    currentIndex = 0;
    document.querySelector('.storyview').style.display = 'none';
    let bars = document.querySelectorAll(".bars");
    bars[0].innerHTML = "";
    bars[1].innerHTML = "";
    images = [];
}

function closeZoom() {
    document.querySelector('.zoom').style.display = 'none';
}

function closeGroupPage() {
    document.querySelector('.group-page').style.display = 'none';
}

function switchImageLeft() {
    const storyImage = document.getElementById('storyImage');
    let loadingbars = document.querySelectorAll(".loading-bar");

    if(currentIndex - 1 >= 0){
        loadingbars[currentIndex].style.visibility = "hidden";
        currentIndex--;
        storyImage.src = images[currentIndex];
    }
}

function switchImageRight() {
    const storyImage = document.getElementById('storyImage');
    let loadingbars = document.querySelectorAll(".loading-bar");

    if(currentIndex + 1 < images.length){
        currentIndex++;
        loadingbars[currentIndex].style.visibility = "visible";
        storyImage.src = images[currentIndex];
        viewStory(currentIndex);
    }
}

function getStories(id) {
    $(document).ready(function () {
        $.ajax({
            url: "controller",
            type: 'post',
            data: {action: "getStories", userId: id},
            success: function (data) {
                images = []
                stories = JSON.parse(data);
                for (var i = 0; i < stories.length; i++) {
                    images[i] = "stories/" + stories[i][2];
                }
                openStoryView(images);
            },
            error: function () {
                alert("Error with ajax");
            }
        });
    });
}

function viewStory(index) {
    var storyId = stories[index][0];
    $(document).ready(function () {
        $.ajax({
            url: "controller",
            type: 'post',
            data: {action: "viewStory", storyId: storyId},
            success: function (data) {

            },
            error: function () {
                alert("Error with ajax");
            }
        });
    });
}

function viewMyStory(storyId) {
    alert("in");
    $(document).ready(function () {
        $.ajax({
            url: "controller",
            type: 'post',
            data: {action: "viewMyStory", storyId: storyId},
            success: function (data) {
                var storyDetails = JSON.parse(data);
                var story =[];
                story[0]="stories/" +storyDetails[1];
                openMyStoryView(story);
                alert("success");
            },
            error: function () {
                alert("Error with ajax");
            }
        });
    });
}

function myStories() {
    var yourStoryList = document.getElementById('yourStoryList');
    $(document).ready(function () {
        $.ajax({
            url: "controller",
            type: 'post',
            data: {action: "getMyStories"},
            success: function (data) {
                yourStoryList.innerHTML = "";
                var myStories = JSON.parse(data);
                for (var i = 0; i < myStories.length; i++) {
                    if (myStories[i][1] == 1) {
                        yourStoryList.innerHTML += "<div class='block' onclick='viewMyStory("+myStories[i][0]+")'> " +
                            "<div class='imgbox'> " +
                            "<img src='stories/"+myStories[i][2]+"' alt='' class='cover'> " +
                            "</div> " +
                            "<div class='details'> " +
                            "<div class='listhead'>" +
                            "<div><h4>" + myStories[i][5] + " views</h4> <p class='time'>" + myStories[i][4] + "</p></div> " +
                            "<ion-icon name='close-outline'></ion-icon></div> " +
                            "</div>" +
                            " </div>";
                    }
                }
            },
            error: function () {
                alert("Error with ajax");
            }
        });
    });

}