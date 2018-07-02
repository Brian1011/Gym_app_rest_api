<?php
/**
 * Created by PhpStorm.
 * User: Brian Mutinda
 * Date: 30/06/2018
 * Time: 04:00 PM
 */
?>

<html>
<head>
    <link rel="stylesheet" href="{{asset('css/app.css')}}">
</head>

<body>
<div class="container">
    <div class="row">
        <div class="col-lg-2"></div>

        <div class="col-lg-6">
            <h1>All Workout Sessions</h1>
            <!--Check for sucess message-->
            @if(session()->has('message'))
                <div class="alert alert-success">
                    {{session()->get('message')}}
                </div>
            @endif

            <table class="table">
                <tr class="success">
                    <th>Workout Id</th>
                    <th>User Id</th>
                    <th>Date</th>
                    <th>Location</th>
                    <th>Workout Name</th>
                    <th>Number of reps</th>
                    <th>Delete Session</th>
                </tr>


                @foreach($workoutsession as $workoutsession)
                    <tr>
                        <td>{{$workoutsession->id}}</td>
                        <td>{{$workoutsession->user_id}}</td>
                        <td>{{$workoutsession->date}}</td>
                        <td>{{$workoutsession->location}}</td>
                        <td>{{$workoutsession->exercise_name}}</td>
                        <td>{{$workoutsession->number_of_reps}}</td>
                        <td><a href="/workoutsession/{{$workoutsession->id}}">Erase</a></td>
                    </tr>
                @endforeach


            </table>

        </div>

        <div class="col-lg-2"></div>
    </div>
</div>
</body>
</html>
