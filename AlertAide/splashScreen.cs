
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;


namespace AlertAide
{
	

	using Android.App;
	using Android.OS;

	[Activity (MainLauncher = true, NoHistory = true, Label = "My splash app", Icon = "@drawable/icon")]	
	public class splashScreen : Activity
	{
		

		private void OnTimedEvent(object sender, System.Timers.ElapsedEventArgs e)
		{

			StartActivity (typeof(MainActivity));
		}

		protected override void OnCreate (Bundle bundle)
		{
			base.OnCreate (bundle);
			SetContentView (Resource.Layout.Main);
			timer = new System.Timers.Timer();
			timer.Interval = 3000;
			timer.AutoReset = false;
			timer.Elapsed += OnTimedEvent;

		}
	

			
}
	

