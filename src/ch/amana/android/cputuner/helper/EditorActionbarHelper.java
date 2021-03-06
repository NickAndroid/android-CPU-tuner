package ch.amana.android.cputuner.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import ch.amana.android.cputuner.R;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;

public class EditorActionbarHelper {

	public enum ExitStatus {
		undefined, save, discard
	}

	public interface EditorCallback {

		void discard();

		void save();

		Context getContext();

		void finish();

	}

	public static void addActions(final EditorCallback ecb, final ActionBar actionBar) {
		actionBar.addAction(new Action() {
			@Override
			public void performAction(View view) {
				ecb.discard();
			}

			@Override
			public int getDrawable() {
				return android.R.drawable.ic_menu_close_clear_cancel;
			}
		});
		actionBar.addAction(new Action() {
			@Override
			public void performAction(View view) {
				ecb.save();
			}

			@Override
			public int getDrawable() {
				return android.R.drawable.ic_menu_save;
			}
		});
	}

	public static void onBackPressed(final EditorCallback ecb, ExitStatus exitStatus, boolean changed) {
		if (changed && exitStatus == ExitStatus.undefined) {
			AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ecb.getContext());
			alertBuilder.setTitle(R.string.msg_title_unsaved_changes);
			alertBuilder.setMessage(R.string.msg_unsaved_changes);
			alertBuilder.setNegativeButton(R.string.no, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					ecb.discard();
				}
			});
			alertBuilder.setPositiveButton(R.string.yes, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					ecb.save();
				}
			});
			AlertDialog alert = alertBuilder.create();
			alert.show();
		} else {
			ecb.finish();
		}
	}

}
