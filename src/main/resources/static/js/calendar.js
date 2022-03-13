let nav = 0;
let clicked = null;
let events = localStorage.getItem('events')?JSON.parse(localStorage.getItem('events')) : []; //parse value (array of event objects) if exists otherwise return empty array


const calendar = document.getElementById("calendar");
const newEventModal = document.getElementById("newEventModal");
const deleteEventModal = document.getElementById("deleteEventModal");
const displayModal = document.getElementById("displayModal");

const eventTitleInput = document.getElementById('eventTitleInput');
const weekdays = ["Monday", "Tuesday", "wednesday", "Thursday", "Friday", "Saturday","Sunday"];
const daysToRemind = [0,1,3,6,14]



function load(){
    const dt = new Date();

    if (nav !== 0){
        dt.setMonth(new Date().getMonth() + nav);
    }
    const day = dt.getDate();
    const month = dt.getMonth();
    const year = dt.getFullYear();

    const firstDayOfMonth = new Date(year, month, 1);
    const daysInMonth = new Date(year, month+1, 0).getDate();

    const dateString = firstDayOfMonth.toLocaleDateString('en-nz',{
        weekday: 'long',
        year: 'numeric',
        momth: 'numeric',
        day: 'numeric',
    });

    //console.log(dateString);
    const paddingDays = weekdays.indexOf(dateString.split(" ")[0]);

     document.getElementById("monthDisplay").innerText =
     `${dt.toLocaleDateString("en-nz",{month:"long"})} ${year}`

     calendar.innerHTML = "";

    for(let i = 1; i <= paddingDays + daysInMonth; i++) {
        const daySquare = document.createElement("div");
        daySquare.classList.add('day');
        const dayString = `${i-paddingDays} ${month+1} ${year}`;

        if(i > paddingDays){

            monthStr = ""+ (month+1);
            const curMonth = monthStr.padStart(2,"0");
            dayStr = ""+(i-paddingDays);
            const curDay = dayStr.padStart(2,"0");
            daySquare.id = ""+year+curMonth + curDay;
            // daySquare.tagName = ""+year+curMonth + curDay;  not sure yet whether add "name" attribute or not
            daySquare.innerText = i - paddingDays;

            if(i-paddingDays === day && nav === 0) {
                daySquare.id = 'currentDay';
            } //highlight today


            const eventsForDay = events.filter(e => e.date === dayString);


            if(eventsForDay.length > 0){
                for(let i = 0; i < eventsForDay.length; i++){
                    const eventDiv = document.createElement("div");

                    const checkBox = document.createElement("input");
                    checkBox.setAttribute("type","checkbox");



                    eventDiv.classList.add('event');
                    eventDiv.innerText = eventsForDay[i].title;
                    eventDiv.appendChild(checkBox);



                    daySquare.appendChild(eventDiv);
                }

            }

            const displayRequestDiv = document.createElement("div");

            var currDate = ""+year+curMonth + curDay;
            var requestLink = /*[[@{}]] */"/calendar/" + currDate; // https://stackoverflow.com/questions/51786267/thymeleaf-use-a-link-with-thhref-in-javascript

            // displayRequestDiv.innerHTML = "<a style='text-decoration: none; color: black;' href=" + requestLink + ">see tasks</a>";
            displayRequestDiv.innerHTML = "<button class=displayTasksBtn type=button name=" + currDate + ">see tasks</button>"

            daySquare.appendChild(displayRequestDiv);

            displayRequestDiv.onclick = function(eventClick){
                eventClick.stopPropagation();
                const displayBanner = document.getElementById('displayModal');

                const existingTitles = document.getElementsByClassName('displayTitle');

                // if(existingTitles.length > 0){
                //     for(let i = 0; i<existingTitles.length;i++){
                //         displayBanner.removeChild(existingTitles[i]);
                //     }
                // }

                dayEvents = events.filter(e=>e.date === dayString);





                // for(let i = 0; i < dayEvents.length; i++){
                //     const eventTitle = document.createElement('div');
                //     eventTitle.className = "displayTitle";
                //     eventTitle.innerHTML = (i+1) + ": " + dayEvents[i].title;
                //     eventTitle.style.fontSize = "large"
                //     eventTitle.style.fontWeight = "normal"
                //     displayBanner.appendChild(eventTitle);
                // }
            }

            daySquare.addEventListener('click',()=>
                openModal(dayString));
        }
        else{
            daySquare.classList.add('padding');
        }

        calendar.appendChild(daySquare);
    }
}

function openModal(date) {
    clicked = date;

    const eventForDay = events.find(e => e.date === clicked);

    if(eventForDay){
        document.getElementById('eventText').innerText = eventForDay.title;
        deleteEventModal.style.display = 'block';
    }
    else{
        newEventModal.style.display = 'block';

        // retrieve clicked day-grid date, and form an input to server
        const tempDateInput = document.createElement("input");
        tempDateInput.setAttribute("style", "display: none;");
        tempDateInput.setAttribute("name", "date");
        tempDateInput.setAttribute("value", date);

        const addTaskForm = document.getElementById("addTaskForm");
        addTaskForm.appendChild(tempDateInput);
    }

    // backDrop.style.display = "block";
}

function saveEvent(){
    if(eventTitleInput.value){
        eventTitleInput.classList.remove("error");

        events.push({
            date:clicked,
            title:eventTitleInput.value,
        })

        localStorage.setItem('events',JSON.stringify(events));

        closeModal();
    }else{
        eventTitleInput.classList.add('error');
    }
}

function closeModal(){
    eventTitleInput.classList.remove("error");
    newEventModal.style.display = 'none';
    deleteEventModal.style.display = 'none';
    eventTitleInput.value = '';
    document.getElementById("taskDetailModal").style.display = "none";

    clicked = null;
    load();
}

function deleteEvent(){
    events = events.filter(e => e.date !== clicked);
    localStorage.setItem('events', JSON.stringify(events));
    closeModal();
}

function initButtons(){
    document.getElementById("nextButton").addEventListener('click',()=>{
        nav++;
        load();
    });
    document.getElementById("backButton").addEventListener('click',()=>{
        nav--;
        load();
    });

    // document.getElementById('saveButton').addEventListener('click',saveEvent);
    document.getElementById('cancelButton').addEventListener('click',closeModal);

    document.getElementById('deleteButton').addEventListener('click',deleteEvent);
    document.getElementById('closeButton').addEventListener('click',closeModal);
    document.getElementById('detailCloseButton').addEventListener('click',closeModal);
}

initButtons();
load();
