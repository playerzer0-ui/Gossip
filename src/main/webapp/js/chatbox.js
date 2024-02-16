function seeProfile(){
    $( ".left" ).css( "z-index", "1" );
    $( ".leftSide" ).css( "z-index", "3" );
    $( ".right" ).css( "z-index", "2" );
}

function seeChatList(){
    $( ".left" ).css( "z-index", "3" );
    $( ".leftSide" ).css( "z-index", "1" );
    $( ".right" ).css( "z-index", "2" );
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

function seeMessage(){
    $( ".left" ).css( "z-index", "2" );
    $( ".leftSide" ).css( "z-index", "1" );
    $( ".right" ).css( "z-index", "3" );
}