import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.receitastok.R
import com.example.receitastok.databinding.ActivityUserProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obter informações do usuário autenticado
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            // Preencher o e-mail
            val email = user.email ?: "Email não disponível"
            binding.tvUserEmail.text = email

            // Gerar nome do usuário a partir do e-mail
            val userName = email.substringBefore("@")
            binding.tvUserName.text = userName

            // Carregar foto de perfil (se disponível)
            val profilePictureUrl = user.photoUrl
            if (profilePictureUrl != null) {
                Picasso.get().load(profilePictureUrl).into(binding.ivProfilePicture)
            } else {
                binding.ivProfilePicture.setImageResource(R.drawable.ic_profile)
            }
        }

        // Dados de receitas (mock para exemplo)
        val userRecipes = listOf("Receita 1", "Receita 2", "Receita 3")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, userRecipes)
        binding.lvRecipes.adapter = adapter

        // Botão para voltar à home
        binding.btnBackToHome.setOnClickListener {
            finish() // Encerra a Activity atual e volta à anterior
        }
    }
}
