<?php

namespace App\Http\Controllers;

use App\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Validator;

class usersController extends Controller
{
    //login in the user
    public function login(Request $request){
        //no need for validation
        $user = new User();

        //check if the email address exists
        $email = $request->input('email');
        $user_pass = $request->input('password');
        $user['email'] = $email;

        if($user = User::where('email','=',$email)->first()){
            /*
             *  email exists
             *  Go to database and select password
             */

            if(Hash::check($user_pass,$user->password)){
                $user['authorize'] = "authorized";
            }else{
                $user['authorize'] = "Not Authorized";
            }

            return response()->json($user);
        }else{
            $user['authorize'] = "Not Authorized";
            return response()->json($user);
        }

    }

    //store a new user
    public function store(Request $request)
    {
        //validate the data
        $validatedData = Validator::make($request->all(), [
            'firstname'=>'required|string',
            'lastname'=>'required|string',
            'email'=>'required|string|email|unique:users_94006,email',
            'gender'=>'required|string',
            'password'=>'required|min:4',
            'age'=>'numeric',
            'weight'=>'numeric',
            'target_weight'=>'numeric',
            'preffered_location'=>'string',
        ]);

        //if validation fails give me an error
        if($validatedData->fails()){

            $response = [
              'status'=>'failed',
              'validate'=>$validatedData->errors()
            ];

            return response()->json($response);
        }

        $user = new User();
        $user->firstname = $request->input('firstname');
        $user->lastname = $request->input('lastname');
        $user->email=$request->input('email');
        $user_password =  $request->input('password');//hash the password
        $user->password = Hash::make($user_password);//send the hashed password to the db
        $user->gender=$request->input('gender');
        $user->weight=$request->input('weight');
        $user->age=$request->input('age');
        $user->target_weight=$request->input('target_weight');
        $user->preffered_location=$request->input('preffered_location');
        $user->api_token = str_random(60);//generate random strings

       if( $user->save()){
           $response = [
               'status'=>'success',
               'user'=>$user
           ];
           return response()->json($response);
       }else{
           $response = [
               'status'=>'failed',
           ];
           return response()->json($response);
       }


    }

    //show a specific user
    public function show($user_id)
    {
        //get the info for specific person
        $user = User::find($user_id);
        return response()->json($user);
    }

    //update user
    public function update(Request $request, $id)
    {

        try{
            //Check whether the user exists
            //$user = new User();
            $user = User::findOrFail($id);

            //get the data from the forms
            //validate the data
            $validatedData = Validator::make($request->all(), [
                'firstname'=>'required|string',
                'lastname'=>'required|string',
                'email'=>'required|string|email',
                'gender'=>'required|string',
                'password'=>'required|min:4',
                'age'=>'numeric',
                'weight'=>'numeric',
                'target_weight'=>'numeric',
                'preffered_location'=>'string',
            ]);

            //if validation fails give me an error
            if($validatedData->fails()){
                return response()->json($validatedData->errors());
            }

            $user->firstname = $request->input('firstname');
            $user->lastname = $request->input('lastname');
            $user->email=$request->input('email');
            $user->password = $request->input('password');
            $user->gender=$request->input('gender');
            $user->weight=$request->input('weight');
            $user->target_weight=$request->input('target_weight');
            $user->preffered_location=$request->input('preffered_location');
            //$user->api_token = str_random(60);//generate random strings

            $user->save();

            return response()->json($user);

        }catch (ModelNotFoundException $err){
            return response()->json($err);
        }

    }

    //erase a user
    public function destroy($id)
    {
        $user = User::find($id);
        $user->delete();

        return response('Account Erased');
    }

    /*WEBSITE VERSION*/
    public function webShowAll(){
        $user = User::all();
        return view('allusers',['users'=>$user]);
    }
}
