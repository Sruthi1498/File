

import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.text.format.Formatter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filemanager.FileAdapter
import com.example.filemanager.OnFileClickListener
import com.example.filemanager.R
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ExternalFragment : Fragment(), OnFileClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fileList: ArrayList<File>
    private lateinit var fileAdapter: FileAdapter
    private lateinit var path: TextView
    private lateinit var store:File
    private lateinit var data:String
    private val PERMISSION_REQUEST_CODE = 100


    private lateinit var view: View
    override fun getView(): View {
        return view
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = inflater.inflate(R.layout.external_fragment,container,false)


        store = File(Environment.getExternalStorageDirectory().toString())

        try {
            data = arguments!!.getString("path").toString()
            val file= File(data)
            store = file

        }
        catch (e:Exception)
        {
            e.stackTrace
        }

if(checkPermission())
{
    displayFiles()
}
        else
            requestPermission()
        return view
    }

   private fun checkPermission():Boolean
   {
       val result = ContextCompat.checkSelfPermission(context!!,android.Manifest.permission.READ_EXTERNAL_STORAGE)
       return result == PackageManager.PERMISSION_GRANTED
   }
    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                context as Activity,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {
            Toast.makeText(
                context,
                "Write External Storage permission allows us to read  ",
                Toast.LENGTH_LONG
            ).show();
        } else {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf<String>(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode)
        {
            PERMISSION_REQUEST_CODE ->
            {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
            }
        }
    }
    private fun findFiles(file: File): ArrayList<File>
     {
        val arrayList: ArrayList<File> = ArrayList()
        val listFiles: Array<out File>? = file.listFiles()
         if(listFiles!= null)
        for(i in listFiles)
        {
            if(i.isDirectory && !i.isHidden)
            {
                arrayList.add(i)
            }

        }
        return arrayList
    }

    private fun displayFiles()
    {
       recyclerView = view.findViewById(R.id.recyle_internal) as RecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
        fileList = ArrayList()
        fileList.addAll(findFiles(store)!!)
        fileAdapter = FileAdapter(context!!,fileList,this)
        recyclerView.adapter = fileAdapter

    }

    override fun onFileClick(file: File) {
        if(file.isDirectory)
        {
            val bundle = Bundle()
            bundle.putString("path",file.absolutePath)
            val externalFragment = ExternalFragment()
            externalFragment.arguments= bundle
            fragmentManager!!.beginTransaction().replace(R.id.frame,externalFragment).addToBackStack("MainActivity").commit()
        }
        else if(file.isFile)
        {
            val alertDialog: AlertDialog.Builder = AlertDialog.Builder(context)
            alertDialog.setTitle("File Details")
            val details = TextView(context)
            alertDialog.setView(details)
            val date = Date(file.lastModified())
            val format = SimpleDateFormat("dd/mm/yyyy HH:mm:ss")
            val date1 = format.format(date)
            details.text = "File name:  ${file.name} \n File Size:  ${Formatter.formatShortFileSize(context,file.length())} \n Last modified: $date1"

        }
    }


}

