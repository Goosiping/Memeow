package com.example.memeow.feature_main.domain.use_case

data class MemeUseCases(
    val getMemes: GetMemes,
    val exploreMemes: ExploreMemes,
    val addMeme: AddMeme,
    val deleteMeme: DeleteMeme,
    val getTagsByUri: GetTagsByUri,
    val addTagsByUri: AddTagsByUri,
    val deleteTagsByUri: DeleteTagsByUri,
    val getAllTags: GetAllTags,
)
