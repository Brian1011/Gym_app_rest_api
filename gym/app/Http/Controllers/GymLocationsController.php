<?php

namespace App\Http\Controllers;

use App\GymLocations;
use Illuminate\Http\Request;

class GymLocationsController extends Controller
{
    /*Display all Locations*/
    public function show_all(){
        $gymlocations = GymLocations::all();
        return response()->json($gymlocations);
    }


    public function store(Request $request)
    {
        $request->validate([
            'gym_name'=>'required|string',
            'long'=>'required',
            'lat'=>'required',
        ]);


        $gymlocations = new GymLocations();
        $gymlocations->gym_name = request('gym_name');
        $gymlocations->latitude = request('lat');
        $gymlocations->longitude = request('long');

        $gymlocations->save();

        return back()->with('message','Data Saved sucessfully');
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */

    /*Web Functionalities*/
    public function webShowAll(){
        $gymlocations = GymLocations::all();
        return view('all_locations',['gymlocations'=>$gymlocations]);
    }
}
