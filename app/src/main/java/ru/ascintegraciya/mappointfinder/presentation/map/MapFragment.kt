package ru.ascintegraciya.mappointfinder.presentation.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.GeoObjectSelectionMetadata
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import kotlinx.coroutines.launch
import ru.ascintegraciya.mappointfinder.data.entity.PointToMap
import ru.ascintegraciya.mappointfinder.databinding.FragmentMapBinding
import ru.ascintegraciya.mappointfinder.utils.setNavigationResult


class MapFragment : Fragment() {

    companion object {
        private const val KEY_POINT_TO_MAP = "POINT_TO_MAP"
        private const val KEY_POINT_TO_MAP_CODE = "POINT_TO_MAP_CODE"

        fun createBundle(pointToMap: PointToMap?, pointToMapCode: String): Bundle {
            return Bundle().apply {
                putParcelable(KEY_POINT_TO_MAP, pointToMap)
                putString(KEY_POINT_TO_MAP_CODE, pointToMapCode)
            }
        }
    }

    private val defaultPoint = Point(59.945933, 30.320045)

    private lateinit var viewModel: MapViewModel
    private lateinit var binding: FragmentMapBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        MapKitFactory.initialize(requireContext())
        binding = FragmentMapBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        setupScreen()
        observeNewPointToMap()
        setListeners()
    }

    private fun observeNewPointToMap() = viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
            viewModel.newPointToMap.collect { wrapper ->
                wrapper.getContentIfNotHandled()?.let { pointToMap ->
                    setNavigationResult(viewModel.pointToMapCode, pointToMap)
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun setupScreen() {
        val pointToMap = viewModel.pointToMap
        val targetPoint = if (pointToMap == null) {
            defaultPoint
        } else {
            Point(pointToMap.latitude, pointToMap.longitude)
        }
        binding.mapView.map.move(
            CameraPosition(targetPoint, 14.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 3f),
            null
        )
    }

    private fun setListeners() {
        binding.mapView.map.addTapListener { geoObjectTapEvent ->
            val selectionMetadata = geoObjectTapEvent
                .geoObject
                .metadataContainer
                .getItem(GeoObjectSelectionMetadata::class.java)

            if (selectionMetadata != null) {
                binding.mapView.map.selectGeoObject(selectionMetadata.id, selectionMetadata.layerId)
            }
            selectionMetadata != null
        }
        binding.mapView.map.addInputListener(
            object : InputListener {
                override fun onMapTap(p0: Map, p1: Point) {
                    binding.mapView.map.deselectGeoObject()
                }

                override fun onMapLongTap(p0: Map, p1: Point) {}
            }
        )
        binding.btnConfirmPoint.setOnClickListener {
            //Здесь необходимо взять координаты отмеченой точки и Адрес и передать в viewModel
            // пока что здесь просто заглушка
            //viewModel.setNewPointToMap(PointToMap(latitude = , longitude = , address =))
            findNavController().popBackStack()
        }
    }

    private fun initViewModel() {
        val pointToMap = arguments?.getParcelable<PointToMap>(KEY_POINT_TO_MAP)
        val pointToMapCode = arguments?.getString(KEY_POINT_TO_MAP_CODE) ?: return
        val factory = MapViewModel.Factory(pointToMap, pointToMapCode)

        viewModel = ViewModelProvider(this, factory)[MapViewModel::class.java]
    }

    override fun onStop() {
        binding.mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        binding.mapView.onStart()
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }
}