$('document').ready(function(){

    $('.table #editButton').on('click',function (event){
        event.preventDefault();

        var href=$(this).attr('href');

        $.get(href)
            .done(function (note){
                debugger
                $('#idEdit').val(note.id)
                $('#titleEdit').val(note.title);
                $('#categoryEdit').val(note.category.name)
                $('#contentEdit').val(note.content)
                $('#dateEdit').val(note.createdAt)
                $('#linkEdit').val(note.link)

        });
        // $.get(href)
        //     .done(function (){
        //         $('#idEdit').val(/*[[${note.id}]]*/);
        //         $('#titleEdit').val(/*[[${note.title}]]*/)
        //         $('#contentEdit').val(/*[[${note.content}]]*/)
        //         $('#dateEdit').val(/*[[${note.createdAt}]]*/)
        //         $('#linkEdit').val(/*[[${note.link}]]*/)
        //
        //     })
        //     .fail(function(jqXHR, textStatus, errorThrown) {
        //         // Code to execute when the request fails
        //         console.error("GET request failed", errorThrown);
        //         // Handle the failure
        //     });

        $('#EditModal').modal();
    });
});