package com.phytal.sarona.ui.courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialElevationScale
import com.phytal.sarona.R
import com.phytal.sarona.data.db.entities.Course
import com.phytal.sarona.data.network.ConnectivityInterceptorImpl
import com.phytal.sarona.data.network.HacApiService
import com.phytal.sarona.databinding.FragmentCoursesBinding
import com.phytal.sarona.ui.base.ScopedFragment
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class CoursesFragment : ScopedFragment(), KodeinAware, CoursesAdapter.CourseAdapterListener {
    override val kodein by closestKodein()
    private val viewModelFactory by instance<CurrentCourseViewModelFactory>()
    private lateinit var viewModel: CourseViewModel
    private var disposable: Disposable? = null
//    private lateinit var courseNetworkDataSource: CourseNetworkDataSourceImpl
    private val adapter = CoursesAdapter(this)
    private lateinit var binding: FragmentCoursesBinding

    private val hacApiServe by lazy {
        HacApiService(
            ConnectivityInterceptorImpl(
                this.requireContext()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoursesBinding.inflate(inflater)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        // init onClickListeners
        binding.backBtn.setOnClickListener {
            Toast.makeText(context, "Back Button Clicked", Toast.LENGTH_LONG).show()
        }

        binding.forwardBtn.setOnClickListener {
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(CourseViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch{
        val currentCourses = viewModel.courses.await()
        currentCourses.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer

            binding.groupLoading.visibility = View.GONE

            adapter.setCourses(it)
        })
    }

//    private fun updateLastUpdated(time: ZonedDateTime) {
//        val formatter = DateTimeFormatter.ofPattern("MM/sarona_logo HH:mm")
//        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Last updated ${time.format(formatter)}"
//    }

    private suspend fun setNewTextOnMainThread(input: String) {
        withContext(Main) {
            binding.textViewLoading.text = input
        }
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    override fun onCourseClick(cardView: View, course: Course) {
        exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }
        val courseCardDetailTransitionName = getString(R.string.course_card_detail_transition_name)
        val extras = FragmentNavigatorExtras(cardView to courseCardDetailTransitionName)
        val directions = CoursesFragmentDirections.actionNavCoursesToNavCourseView(course.course)
        findNavController().navigate(directions, extras)
    }
}
