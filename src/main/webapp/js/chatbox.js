var inboxType = 0;
var imgInp = document.getElementById("imgInp");
var groupProfilePic = document.getElementById("groupProfilePic");

function seeProfile(){
    $( ".left" ).css( "z-index", "1" );
    $( ".leftUpdate" ).css( "z-index", "1" );
    $( ".leftSide" ).css( "z-index", "3" );
    $( ".right" ).css( "z-index", "2" );
}

function seeChatList(test){
    $( ".left" ).css( "z-index", "3" );
    $( ".leftUpdate" ).css( "z-index", "1" );
    $( ".leftSide" ).css( "z-index", "1" );
    $( ".right" ).css( "z-index", "2" );
    closeInbox();
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

function closeZoom() {
    document.querySelector('.zoom').style.display = 'none';
}

function closeGroupPage() {
    document.querySelector('.group-page').style.display = 'none';
}
