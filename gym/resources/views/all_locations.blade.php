<?php
/**
 * Created by PhpStorm.
 * User: Brian Mutinda
 * Date: 30/06/2018
 * Time: 04:11 PM
 */
?>

<html>
<head>
    <link rel="stylesheet" href="{{asset('css/app.css')}}">
</head>

<body>
<div class="container">
    <div class="row">
        <div class="col-lg-3"></div>

        <div class="col-lg-6">
            <h1>All Gym Locations</h1>

            <table class="table">
                <tr class="success">
                    <th>Gym Id</th>
                    <th>Longitude</th>
                    <th>Latitude</th>
                    <th>Gym Name</th>
                </tr>

                @foreach($gymlocations as $gymlocation)
                <tr>
                    <td>{{$gymlocation->id}}</td>
                    <td>{{$gymlocation->longitude}}</td>
                    <td>{{$gymlocation->latitude}}</td>
                    <td>{{$gymlocation->gym_name}}</td>
                </tr>
                @endforeach
            </table>

        </div>

        <div class="col-lg-3"></div>
    </div>
</div>
</body>
</html>

