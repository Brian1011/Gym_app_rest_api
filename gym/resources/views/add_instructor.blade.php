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
                    
                <!--Check for image name-->
                    @if(session()->has('path'))
                        <div class="alert alert-sucess">
                            {{session()->get('path')}}
                            <img src="/images/{{Session::get('path')}}">
                        </div>
                    @endif

                    <h1>Register New Instructor</h1>
                    <form class="form" method="post" action="/instructor/save" enctype="multipart/form-data">
                        {{csrf_field()}}

                        <div class="form-group">
                            <input type="file" name="select_file">
                        </div>

                        <div class="form-group">
                            <input type="text" placeholder="Full Name" name="full_name" class="form-control">
                        </div>

                        <div class="form-group">
                            <input type="text" placeholder="Contacts" name="contacts" class="form-control">
                        </div>

                        <div class="form-group">
                            <input type="text" placeholder="Email" name="email" class="form-control">
                        </div>

                        <div class="form-group">
                            <select name="gender" class="form-control">
                                <option vale="Male">Male</option>
                                <option value="Female">Female</option>
                                <option value="Others">Others</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <select name="gym_id" class="form-control">
                                @foreach($gyms as $gym)
                                    <option value="{{$gym->id}}">{{$gym->gym_name}}</option>
                                @endforeach
                            </select>
                        </div>

                        <div class="form-group">
                            <input type="submit" value="Submit" class="btn btn-primary">
                        </div>
                    </form>
                </div>
                <div class="col-lg-3"></div>
            </div>
        </div>
    </body>
</html>
