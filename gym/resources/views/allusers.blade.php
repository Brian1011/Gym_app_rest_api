<?php
/**
 * Created by PhpStorm.
 * User: Brian Mutinda
 * Date: 30/06/2018
 * Time: 03:24 PM
 */
?>

<html>
    <head>
        <link rel="stylesheet" href="{{asset('css/app.css')}}">
    </head>

    <body>
        <div class="container">
            <div class="row">

                <div class="col-lg-8">

                    <h1>All users</h1>
                    <table class="table">

                        <tr class="success">
                            <th>User ID</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Email</th>
                            <th>Password</th>
                            <th>Age</th>
                            <th>Gender</th>
                            <th>Weight</th>
                            <th>Target Weight</th>
                            <th>Preffered Location</th>
                            <th>Api Token</th>
                            <th>Delete Account</th>
                        </tr>

                        @foreach($users as $user)
                            <tr>
                                <td>{{$user->id}}</td>
                                <td>{{$user->firstname}}</td>
                                <td>{{$user->lastname}}</td>
                                <td>{{$user->email}}</td>
                                <td>{{$user->password}}</td>
                                <td>{{$user->age}}</td>
                                <td>{{$user->gender}}</td>
                                <td>{{$user->weight}}</td>
                                <td>{{$user->target_weight}}</td>
                                <td>{{$user->preffered_location}}</td>
                                <td>{{$user->api_token}}</td>
                                <td><a href="">Delete</a></td>
                            </tr>
                        @endforeach

                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
