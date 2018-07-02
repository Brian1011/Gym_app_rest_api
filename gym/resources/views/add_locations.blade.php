<?php
/**
 * Created by PhpStorm.
 * User: Brian Mutinda
 * Date: 30/06/2018
 * Time: 04:37 PM
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
                <h1>New Gym Location</h1>
                <!--Check if there are errors-->
                @if ($errors->any())
                    <div class="alert alert-danger">
                        <ul>
                            <!--if errors exist print all of them-->
                            @foreach ($errors->all() as $error)
                                <li>{{ $error }}</li>
                            @endforeach
                        </ul>
                    </div>
                @endif

            <!--Check for sucess message-->
                @if(session()->has('message'))
                    <div class="alert alert-success">
                        {{session()->get('message')}}
                    </div>
                @endif

                <form class="form" method="post" action="/location/save">
                    {{csrf_field()}}

                    <div class="form-group">
                        <input type="text" placeholder="Gym Name" name="gym_name" class="form-control">
                    </div>

                    <div class="form-group">
                        <input type="text" placeholder="Longitude" name="long" class="form-control">
                    </div>

                    <div class="form-group">
                        <input type="text" placeholder="Latitude" name="lat" class="form-control">
                    </div>

                    <div class="form-group">
                        <input type="submit" value="Submit" class="btn btn-lg btn-primary">
                    </div>

                </form>
            </div>

            <div class="col-lg-3"></div>
        </div>

</div>
</body>
</html>

