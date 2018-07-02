<?php
/**
 * Created by PhpStorm.
 * User: Brian Mutinda
 * Date: 30/06/2018
 * Time: 03:31 PM
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

        <div class="col-lg-8">
            <h1>All Instructors</h1>
            <!--Check for sucess message-->
            @if(session()->has('message'))
                <div class="alert alert-success">
                    {{session()->get('message')}}
                </div>
            @endif

            <table class="table table-success">
                <tr class="success">
                    <th>Photo</th>
                    <th>Instructor Id</th>
                    <th>Gym Id</th>
                    <th>Gym Location</th>
                    <th>Name</th>
                    <th>Contacts</th>
                    <th>Email</th>
                    <th>Gender</th>
                    <th>Delete</th>
                </tr>

                @foreach($instructor as $instructor)
                    <tr>
                        <td><img src="/images/{{$instructor->photo}}" style="height: 50px; width: 50px;"></td>
                        <td>{{$instructor->id}}</td>
                        <td>{{$instructor->gym_id}}</td>
                        <td>{{$instructor->gym_name}}</td>
                        <td>{{$instructor->name}}</td>
                        <td>{{$instructor->contacts}}</td>
                        <td>{{$instructor->email}}</td>
                        <td>{{$instructor->gender}}</td>
                        <td><a href="/instructor/{{$instructor->id}}">Delete</a></td>
                    </tr>
                @endforeach
            </table>

        </div>

        <div class="col-lg-3"></div>
    </div>
</div>
</body>
</html>

