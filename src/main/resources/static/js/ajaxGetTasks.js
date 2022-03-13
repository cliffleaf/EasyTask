GET: $(document).ready(function() {

    // GET REQUEST
    $(".displayTasksBtn").each(function(){
        var btn = this;
        this.addEventListener("click", function(event) {
            event.preventDefault();
            var date = this.name;
            ajaxGet(date);
        });
    });


    // DO GET
    function ajaxGet(date) {
        $.ajax({
            type : "GET",
            url : "/calendar/" + date,
            success : function(result) {
                if (result.status == "success") {
                    $('#displayModal').empty();
                    var custList = "";
                    $.each(result.data, function(i, task) {

                                // var t = task.name + "<br>";

                                var displayModalList = document.createElement("div");
                                displayModalList.setAttribute("class", "displayModalList");
                                console.log(task.isComplete)
                                if (task.isComplete) {
                                    displayModalList.setAttribute("style", "background-color: green");
                                } else {
                                    displayModalList.setAttribute("style", "background-color: #C4C4C4");
                                }


                                var innerDiv = document.createElement("div");
                                    var taskNameAnchor = document.createElement("a");
                                        taskNameAnchor.setAttribute("style", "display: inline-block");
                                        taskNameAnchor.setAttribute("id", task.id);
                                        taskNameAnchor.setAttribute("value", task.description);
                                        taskNameAnchor.setAttribute("onclick", "displayDetail("+task.description+")");
                                        taskNameAnchor.innerHTML = task.name;
                                innerDiv.appendChild(taskNameAnchor);

                                    var taskEditDiv = document.createElement("div");
                                        var editAnchor = document.createElement("a");
                                            editAnchor.setAttribute("class", "task-edit-anchor");
                                            editAnchor.setAttribute("href", "/calendar/edit/"+task.id);
                                            editAnchor.innerHTML = "Edit";

                                        var deleteAnchor = document.createElement("a");
                                            deleteAnchor.setAttribute("class", "task-edit-anchor");
                                            deleteAnchor.setAttribute("href", "/calendar/delete/"+task.id);
                                            deleteAnchor.innerHTML = "Delete";

                                        var completeAnchor = document.createElement("a");
                                            completeAnchor.setAttribute("class", "task-edit-anchor");
                                            completeAnchor.setAttribute("href", "/calendar/complete/"+task.id+"/"+task.isComplete);
                                            completeAnchor.innerHTML = "Complete";
                                    taskEditDiv.appendChild(editAnchor);
                                    taskEditDiv.appendChild(deleteAnchor);
                                    taskEditDiv.appendChild(completeAnchor);
                                innerDiv.appendChild(taskEditDiv);
                                displayModalList.appendChild(innerDiv);

                                $('#displayModal').append(displayModalList);
                                // $("#displayModal").append(t);
                            });
                    console.log("Success: ", result);
                }
                else {
                    $("#displayModal").html("<strong>Error</strong>");
                    console.log("Fail: ", result);
                }
            },

            error : function(e) {
                $("#displayModal").html("<strong>Error</strong>");
                console.log("ERROR: ", e);
            }
        });
    }
});
