package com.mmk.profile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.mmk.common.ui.fragmentdelegations.viewBinding
import com.mmk.profile.R
import com.mmk.profile.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private val binding by viewBinding(FragmentProfileBinding::inflate)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        with(binding.profileComposeView) {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ProfileScreen()
            }
        }
        return binding.root
    }

    @Composable
    private fun ProfileScreen() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_person),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = stringResource(id = R.string.profile_name),
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 16.dp),
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .height(68.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.profile_bio),
                    fontSize = 16.sp
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun ProfileScreenPreview() {
        MaterialTheme {
            ProfileScreen()
        }
    }
}
