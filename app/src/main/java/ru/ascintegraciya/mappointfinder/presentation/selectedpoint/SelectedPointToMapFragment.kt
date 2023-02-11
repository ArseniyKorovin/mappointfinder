package ru.ascintegraciya.mappointfinder.presentation.selectedpoint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.ascintegraciya.mappointfinder.R
import ru.ascintegraciya.mappointfinder.data.entity.PointToMap
import ru.ascintegraciya.mappointfinder.databinding.FragmentSelectedPointToMapBinding
import ru.ascintegraciya.mappointfinder.presentation.map.MapFragment
import ru.ascintegraciya.mappointfinder.utils.getNavigationResult

class SelectedPointToMapFragment : Fragment() {

    companion object {
        private const val POINT_TO_MAP_CODE = "POINT_TO_MAP_CODE"
    }

    private val viewModel: SelectedPointToMapViewModel by viewModels()
    private lateinit var binding: FragmentSelectedPointToMapBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSelectedPointToMapBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observePointToMapResult()
        observePointToMap()
        setListener()
    }

    private fun observePointToMap() = viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
            viewModel.pointToMap.collect { wrapper ->
                wrapper.getContentIfNotHandled()?.let { pointToMap ->
                    binding.tvSelectedPoint.text = getString(
                        R.string.title_point,
                        pointToMap.address,
                        pointToMap.latitude.toString(),
                        pointToMap.longitude.toString()
                    )
                }
            }
        }
    }

    private fun observePointToMapResult() = viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
            getNavigationResult<PointToMap>(
                POINT_TO_MAP_CODE
            )?.observe(viewLifecycleOwner) { pointToMap ->
                pointToMap?.let { viewModel.setPointToMap(it) }
            }
        }
    }

    private fun setListener() {
        binding.btnShowToMap.setOnClickListener {
            findNavController().navigate(
                R.id.navigation_map,
                MapFragment.createBundle(viewModel.pointToMap.value.peekContent(), POINT_TO_MAP_CODE)
            )
        }
    }
}