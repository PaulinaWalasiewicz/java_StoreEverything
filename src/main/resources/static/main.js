$(document).ready(function() {
    $('form#newNotesave').on('submit', function(event) {
        debugger
        event.preventDefault();
        // Clear previous error messages
        clearErrorMessages2();
        formatDateTime2()
        // Perform your own validation logic here if needed

        // Make an AJAX request to submit the form data
        $.ajax({
            url: $(this).attr('action'),
            method: $(this).attr('method'),
            data: $(this).serialize(),
            success: function(response) {
                // Handle successful submission
                $('#myModal').modal('hide');
                location.reload(); // Reload the page


            },
            error: function(xhr, status, error) {
                if (xhr.status === 400) {
                    const errors = xhr.responseJSON;
                    displayErrors2(errors);
                } else {
                    // Handle other error scenarios
                }
            }
        });

        // Function to clear all error messages
        function clearErrorMessages2() {
            $('#newtitleError').text('');
            $('#newlinkError').text('');
            $('#newcontentError').text('');
            $('#newcategoryError').text('');

        }
        function formatDateTime2() {
            const now = new Date();

            const year = now.getFullYear();
            const month = String(now.getMonth() + 1).padStart(2, '0');
            const day = String(now.getDate()).padStart(2, '0');
            const hours = String(now.getHours()).padStart(2, '0');
            const minutes = String(now.getMinutes()).padStart(2, '0');
            return `${year}-${month}-${day}T${hours}:${minutes}`;
        }

        // Function to display validation errors
        function displayErrors2(errors) {
            clearErrorMessages2();

            // Iterate over the errors object and update the corresponding error elements
            for (let fieldName in errors) {
                let errorMessage = errors[fieldName];
                let errorElementId ='new'+ fieldName + 'Error';
                $('#' + errorElementId).text(errorMessage);
            }
        }
    });

    $('.table #editButton').on('click', function(event) {
        event.preventDefault();
        const href = $(this).attr('href');
        $.get(href)
            .done(function(note) {
                generateEditNoteModalContent(note); // Call the function to generate modal content
                $('#EditModal').modal();
            });
    });

    // Function to clear all error messages
    function clearErrorMessages() {
        $('#titleEditError').text('');
        $('#categoryEditError').text('');
        $('#contentEditError').text('');
        $('#linkEditError').text('');
        $('#createdAtEditError').text('');
    }

    // Function to display validation errors
    function displayErrors(errors) {
        clearErrorMessages();

        // Iterate over the errors object and update the corresponding error elements
        for (let fieldName in errors) {
            let errorMessage = errors[fieldName];
            let errorElementId = fieldName + 'EditError';
            $('#' + errorElementId).text(errorMessage);
        }
    }

    function generateEditNoteModalContent(note) {
        $('#idEdit').val(note.id);
        $('#titleEdit').val(note.title);
        $('#contentEdit').val(note.content);
        $('#linkEdit').val(note.link);
        $('#dateEdit').val(formatDateTime(note.createdAt));

        // Extract the category ID from the note object
        const categoryId = note.category.id;

        // Find the corresponding option in the select dropdown and set it as selected
        $('#categoryEdit').find(`option[value="${categoryId}"]`).prop('selected', true);
    }

    // Function to format date and time
    function formatDateTime(dateString) {
        const parts = dateString.split(' ');
        const dateParts = parts[0].split('-');
        const timeParts = parts[1].split(':');

        const year = dateParts[2];
        const month = dateParts[1];
        const day = dateParts[0];
        const hours = timeParts[0];
        const minutes = timeParts[1];

        return `${year}-${month}-${day}T${hours}:${minutes}`;
    }


    // Handle form submission and validation errors
    $('form#EditModalNote').on('submit', function(event) {
        debugger
        if (event.currentTarget.id == 'newNotesave') {
            newNote(event)
        } else {
            event.preventDefault();

            // Clear previous error messages
            clearErrorMessages();

            // Perform your own validation logic here if needed

            // Make an AJAX request to submit the form data
            $.ajax({
                url: $(this).attr('action'),
                method: $(this).attr('method'),
                data: $(this).serialize(),
                success: function (response) {
                    // Handle successful submission
                    $('#EditModal').modal('hide');
                    location.reload(); // Reload the page


                },
                error: function (xhr, status, error) {
                    if (xhr.status === 400) {
                        const errors = xhr.responseJSON;
                        displayErrors(errors);
                    } else {
                        // Handle other error scenarios
                    }
                }
            });
        }
    });
});

