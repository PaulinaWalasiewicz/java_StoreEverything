$(document).ready(function() {
    $('.table #editButton').on('click', function(event) {
        event.preventDefault();
        const href = $(this).attr('href');
        $.get(href)
            .done(function(note) {
                generateEditNoteModalContent(note); // Call the function to generate modal content
                $('#EditModal').modal();
            });
    });});

function generateEditNoteModalContent(note) {
    $('#idEdit').val(note.id);
    $('#titleEdit').val(note.title);

    // Extract the category ID from the note object
    const categoryId = note.category.id;

    // Find the corresponding option in the select dropdown and set it as selected
    $('#categoryEdit').find(`option[value="${categoryId}"]`).prop('selected', true);

    $('#contentEdit').val(note.content);
    $('#dateEdit').val(formatDateTime(note.createdAt));
    $('#linkEdit').val(note.link);
}
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
