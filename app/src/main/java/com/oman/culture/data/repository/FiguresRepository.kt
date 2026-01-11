package com.oman.culture.data.repository

import android.content.Context
import com.google.gson.Gson
import com.oman.culture.data.model.Category
import com.oman.culture.data.model.Figure
import com.oman.culture.data.model.FiguresResponse

class FiguresRepository(private val context: Context? = null) {
    
    private val figures: List<Figure> by lazy {
        //  HISTORICAL_LEADERS, POETS_WRITERS, ARTISTS, SPORTS, SCHOLARS, MODERN
        loadFiguresFromJson() ?: defaultFigures
    }
    
    private fun loadFiguresFromJson(): List<Figure>? {
        return try {
            context?.assets?.open("figures.json")?.bufferedReader()?.use { reader ->
                val json = reader.readText()
                val response = Gson().fromJson(json, FiguresResponse::class.java)
                response.figures.map { it.toFigure() }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    private val defaultFigures = listOf(
        // Historical Leaders
        Figure(
            id = 1,
            nameEn = "Sultan Qaboos bin Said",
            nameAr = "السلطان قابوس بن سعيد",
            category = Category.HISTORICAL_LEADERS,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/52/Qaboos_bin_Said.jpg/440px-Qaboos_bin_Said.jpg",
            descriptionEn = "The Sultan of Oman from 1970 to 2020",
            descriptionAr = "سلطان عُمان من 1970 إلى 2020",
            biographyEn = "Sultan Qaboos bin Said Al Said was the Sultan of Oman from 23 July 1970 until his death in 2020. He rose to power after overthrowing his father, Said bin Taimur, in a palace coup in 1970. He was the longest-serving ruler in the Middle East and the Arab world at the time of his death.",
            biographyAr = "السلطان قابوس بن سعيد آل سعيد كان سلطان عُمان من 23 يوليو 1970 حتى وفاته في 2020. وصل إلى السلطة بعد الإطاحة بوالده سعيد بن تيمور في انقلاب قصر عام 1970. كان أطول حاكم خدمة في الشرق الأوسط والعالم العربي وقت وفاته.",
            achievementsEn = listOf(
                "Modernized Oman's infrastructure",
                "Established diplomatic relations worldwide",
                "Founded Sultan Qaboos University",
                "Developed healthcare and education systems"
            ),
            achievementsAr = listOf(
                "تحديث البنية التحتية لعُمان",
                "إقامة علاقات دبلوماسية عالمية",
                "تأسيس جامعة السلطان قابوس",
                "تطوير أنظمة الرعاية الصحية والتعليم"
            ),
            era = "1940-2020",
            yearsActive = 50,
            worksCount = 12
        ),
        Figure(
            id = 2,
            nameEn = "Said bin Sultan",
            nameAr = "سعيد بن سلطان",
            category = Category.HISTORICAL_LEADERS,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/Seyyid_Said%2C_Sultan_of_Muscat_and_Oman.jpg/440px-Seyyid_Said%2C_Sultan_of_Muscat_and_Oman.jpg",
            descriptionEn = "Sultan of Muscat and Oman (1806-1856)",
            descriptionAr = "سلطان مسقط وعُمان (1806-1856)",
            biographyEn = "Said bin Sultan was the Sultan of Muscat and Oman from 1806 to 1856. He built a commercial empire and moved his capital to Zanzibar, creating a maritime trading network across the Indian Ocean.",
            biographyAr = "سعيد بن سلطان كان سلطان مسقط وعُمان من 1806 إلى 1856. بنى إمبراطورية تجارية ونقل عاصمته إلى زنجبار، مُنشئًا شبكة تجارة بحرية عبر المحيط الهندي.",
            achievementsEn = listOf(
                "Expanded Omani influence to East Africa",
                "Established Zanzibar as a trading hub",
                "Signed treaties with Western powers"
            ),
            achievementsAr = listOf(
                "توسيع النفوذ العُماني إلى شرق أفريقيا",
                "تأسيس زنجبار كمركز تجاري",
                "توقيع معاهدات مع القوى الغربية"
            ),
            era = "1791-1856",
            yearsActive = 50,
            worksCount = 8
        ),
        Figure(
            id = 3,
            nameEn = "Sultan Haitham bin Tariq",
            nameAr = "السلطان هيثم بن طارق",
            category = Category.HISTORICAL_LEADERS,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8a/Haitham_bin_Tariq_Al_Said.jpg/440px-Haitham_bin_Tariq_Al_Said.jpg",
            descriptionEn = "Current Sultan of Oman since 2020",
            descriptionAr = "سلطان عُمان الحالي منذ 2020",
            biographyEn = "Sultan Haitham bin Tariq Al Said is the current Sultan of Oman, ascending to the throne on 11 January 2020 following the death of Sultan Qaboos. He previously served as Minister of Heritage and Culture.",
            biographyAr = "السلطان هيثم بن طارق آل سعيد هو سلطان عُمان الحالي، اعتلى العرش في 11 يناير 2020 بعد وفاة السلطان قابوس. شغل سابقًا منصب وزير التراث والثقافة.",
            achievementsEn = listOf(
                "Launched Oman Vision 2040",
                "Economic diversification initiatives",
                "Continued modernization efforts"
            ),
            achievementsAr = listOf(
                "إطلاق رؤية عُمان 2040",
                "مبادرات التنويع الاقتصادي",
                "استمرار جهود التحديث"
            ),
            era = "1954-Present",
            yearsActive = 4,
            worksCount = 5
        ),
        
        // Poets & Writers
        Figure(
            id = 4,
            nameEn = "Abu Muslim al-Bahlani",
            nameAr = "أبو مسلم البهلاني",
            category = Category.POETS_WRITERS,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/No_image_available.svg/300px-No_image_available.svg.png",
            descriptionEn = "Renowned Omani poet and scholar",
            descriptionAr = "شاعر وعالم عُماني مشهور",
            biographyEn = "Abu Muslim Nasir bin Salim bin Udayyim al-Rawahi al-Bahlani was one of the most famous Omani poets. His poetry covered religious, social, and patriotic themes.",
            biographyAr = "أبو مسلم ناصر بن سالم بن عديم الرواحي البهلاني كان من أشهر الشعراء العُمانيين. تناول شعره موضوعات دينية واجتماعية ووطنية.",
            achievementsEn = listOf(
                "Authored numerous poetry collections",
                "Contributed to Omani literary heritage",
                "Influenced generations of poets"
            ),
            achievementsAr = listOf(
                "تأليف مجموعات شعرية عديدة",
                "المساهمة في التراث الأدبي العُماني",
                "التأثير على أجيال من الشعراء"
            ),
            era = "1860-1920",
            yearsActive = 40,
            worksCount = 15
        ),
        Figure(
            id = 5,
            nameEn = "Abdullah al-Tai",
            nameAr = "عبدالله الطائي",
            category = Category.POETS_WRITERS,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/No_image_available.svg/300px-No_image_available.svg.png",
            descriptionEn = "Pioneer of modern Omani literature",
            descriptionAr = "رائد الأدب العُماني الحديث",
            biographyEn = "Abdullah al-Tai was a pioneering Omani writer and journalist who played a significant role in the development of modern Omani literature and media.",
            biographyAr = "عبدالله الطائي كان كاتبًا وصحفيًا عُمانيًا رائدًا لعب دورًا مهمًا في تطوير الأدب والإعلام العُماني الحديث.",
            achievementsEn = listOf(
                "Founded early Omani newspapers",
                "Wrote influential novels and stories",
                "Documented Omani history and culture"
            ),
            achievementsAr = listOf(
                "تأسيس الصحف العُمانية الأولى",
                "كتابة روايات وقصص مؤثرة",
                "توثيق التاريخ والثقافة العُمانية"
            ),
            era = "1924-1973",
            yearsActive = 30,
            worksCount = 20
        ),
        
        // Artists & Musicians
        Figure(
            id = 6,
            nameEn = "Salim bin Ali al-Wahaibi",
            nameAr = "سالم بن علي الوهيبي",
            category = Category.ARTISTS,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/No_image_available.svg/300px-No_image_available.svg.png",
            descriptionEn = "Traditional Omani musician",
            descriptionAr = "موسيقي عُماني تقليدي",
            biographyEn = "A master of traditional Omani music, known for preserving and performing classical Omani musical traditions.",
            biographyAr = "أستاذ في الموسيقى العُمانية التقليدية، معروف بالحفاظ على التقاليد الموسيقية العُمانية الكلاسيكية وأدائها.",
            achievementsEn = listOf(
                "Preserved traditional Omani music",
                "Trained new generations of musicians",
                "Performed at national events"
            ),
            achievementsAr = listOf(
                "الحفاظ على الموسيقى العُمانية التقليدية",
                "تدريب أجيال جديدة من الموسيقيين",
                "الأداء في المناسبات الوطنية"
            ),
            era = "1950-Present",
            yearsActive = 50,
            worksCount = 25
        ),
        
        // Sports Icons
        Figure(
            id = 7,
            nameEn = "Ali Al-Habsi",
            nameAr = "علي الحبسي",
            category = Category.SPORTS,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8e/Ali_Al-Habsi.jpg/440px-Ali_Al-Habsi.jpg",
            descriptionEn = "Legendary Omani football goalkeeper",
            descriptionAr = "حارس مرمى عُماني أسطوري",
            biographyEn = "Ali Abdullah Hamed Al-Habsi is an Omani professional footballer who played as a goalkeeper. He had a successful career in the English Premier League with clubs like Bolton Wanderers and Wigan Athletic.",
            biographyAr = "علي عبدالله حامد الحبسي هو لاعب كرة قدم عُماني محترف لعب كحارس مرمى. حقق مسيرة ناجحة في الدوري الإنجليزي الممتاز مع أندية مثل بولتون واندررز وويغان أثلتيك.",
            achievementsEn = listOf(
                "First Omani to play in Premier League",
                "Multiple Omani Player of the Year awards",
                "Over 100 caps for national team"
            ),
            achievementsAr = listOf(
                "أول عُماني يلعب في الدوري الممتاز",
                "جوائز متعددة لأفضل لاعب عُماني",
                "أكثر من 100 مباراة دولية"
            ),
            era = "1981-Present",
            yearsActive = 20,
            worksCount = 100
        ),
        Figure(
            id = 8,
            nameEn = "Ahmed Al-Maashani",
            nameAr = "أحمد المعشني",
            category = Category.SPORTS,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/No_image_available.svg/300px-No_image_available.svg.png",
            descriptionEn = "Omani football striker",
            descriptionAr = "مهاجم كرة قدم عُماني",
            biographyEn = "Ahmed Al-Maashani is an Omani professional footballer known for his goal-scoring abilities and contributions to the national team.",
            biographyAr = "أحمد المعشني هو لاعب كرة قدم عُماني محترف معروف بقدراته التهديفية ومساهماته في المنتخب الوطني.",
            achievementsEn = listOf(
                "Top scorer in Omani league",
                "Key player for national team",
                "Multiple championship titles"
            ),
            achievementsAr = listOf(
                "هداف الدوري العُماني",
                "لاعب أساسي في المنتخب الوطني",
                "ألقاب بطولات متعددة"
            ),
            era = "1990-Present",
            yearsActive = 15,
            worksCount = 50
        ),
        
        // Scientists & Scholars
        Figure(
            id = 9,
            nameEn = "Ahmed bin Majid",
            nameAr = "أحمد بن ماجد",
            category = Category.SCHOLARS,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/No_image_available.svg/300px-No_image_available.svg.png",
            descriptionEn = "Famous Arab navigator and cartographer",
            descriptionAr = "ملاح ورسام خرائط عربي شهير",
            biographyEn = "Ahmad ibn Majid was an Arab navigator and cartographer born in Julfar (present-day UAE/Oman region). He is considered one of the greatest navigators in history and wrote numerous texts on navigation.",
            biographyAr = "أحمد بن ماجد كان ملاحًا ورسام خرائط عربيًا ولد في جلفار. يُعتبر من أعظم الملاحين في التاريخ وكتب نصوصًا عديدة عن الملاحة.",
            achievementsEn = listOf(
                "Authored 'Kitab al-Fawa'id'",
                "Guided Vasco da Gama to India",
                "Advanced maritime navigation techniques"
            ),
            achievementsAr = listOf(
                "تأليف 'كتاب الفوائد'",
                "إرشاد فاسكو دا غاما إلى الهند",
                "تطوير تقنيات الملاحة البحرية"
            ),
            era = "1421-1500",
            yearsActive = 50,
            worksCount = 40
        ),
        
        // Modern Influencers
        Figure(
            id = 10,
            nameEn = "Mona Al-Said",
            nameAr = "منى آل سعيد",
            category = Category.MODERN,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/No_image_available.svg/300px-No_image_available.svg.png",
            descriptionEn = "Omani businesswoman and entrepreneur",
            descriptionAr = "سيدة أعمال ورائدة أعمال عُمانية",
            biographyEn = "A prominent Omani businesswoman who has contributed to the economic development of Oman through various entrepreneurial ventures.",
            biographyAr = "سيدة أعمال عُمانية بارزة ساهمت في التنمية الاقتصادية لعُمان من خلال مشاريع ريادية متنوعة.",
            achievementsEn = listOf(
                "Founded successful businesses",
                "Promoted women entrepreneurship",
                "Contributed to economic diversification"
            ),
            achievementsAr = listOf(
                "تأسيس أعمال ناجحة",
                "تعزيز ريادة الأعمال النسائية",
                "المساهمة في التنويع الاقتصادي"
            ),
            era = "1970-Present",
            yearsActive = 25,
            worksCount = 10
        ),
        
        // Additional Historical Leaders
        Figure(
            id = 11,
            nameEn = "Imam Ahmed bin Said",
            nameAr = "الإمام أحمد بن سعيد",
            category = Category.HISTORICAL_LEADERS,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/No_image_available.svg/300px-No_image_available.svg.png",
            descriptionEn = "Founder of the Al Said dynasty",
            descriptionAr = "مؤسس سلالة آل سعيد",
            biographyEn = "Imam Ahmed bin Said Al Busaidi was the founder of the current ruling Al Said dynasty of Oman. He united Oman and expelled the Persians, establishing a powerful maritime state.",
            biographyAr = "الإمام أحمد بن سعيد البوسعيدي كان مؤسس سلالة آل سعيد الحاكمة حاليًا في عُمان. وحّد عُمان وطرد الفرس، مؤسسًا دولة بحرية قوية.",
            achievementsEn = listOf(
                "Founded the Al Said dynasty in 1744",
                "Expelled Persian invaders from Oman",
                "Established Omani naval supremacy",
                "United the country under one rule"
            ),
            achievementsAr = listOf(
                "تأسيس سلالة آل سعيد عام 1744",
                "طرد الغزاة الفرس من عُمان",
                "تأسيس السيادة البحرية العُمانية",
                "توحيد البلاد تحت حكم واحد"
            ),
            era = "1694-1783",
            yearsActive = 39,
            worksCount = 6
        ),
        
        // Additional Poets & Writers
        Figure(
            id = 12,
            nameEn = "Hilal bin Badr al-Busaidi",
            nameAr = "هلال بن بدر البوسعيدي",
            category = Category.POETS_WRITERS,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/No_image_available.svg/300px-No_image_available.svg.png",
            descriptionEn = "Contemporary Omani poet laureate",
            descriptionAr = "شاعر عُماني معاصر",
            biographyEn = "Hilal bin Badr al-Busaidi is a celebrated contemporary Omani poet known for his eloquent Arabic poetry that celebrates Omani heritage and culture.",
            biographyAr = "هلال بن بدر البوسعيدي شاعر عُماني معاصر مشهور بشعره العربي البليغ الذي يحتفي بالتراث والثقافة العُمانية.",
            achievementsEn = listOf(
                "Published multiple poetry collections",
                "Won national poetry awards",
                "Represented Oman in Arab poetry festivals"
            ),
            achievementsAr = listOf(
                "نشر مجموعات شعرية متعددة",
                "فاز بجوائز شعرية وطنية",
                "مثّل عُمان في مهرجانات الشعر العربي"
            ),
            era = "1960-Present",
            yearsActive = 45,
            worksCount = 18
        ),
        Figure(
            id = 13,
            nameEn = "Jokha Alharthi",
            nameAr = "جوخة الحارثي",
            category = Category.POETS_WRITERS,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/No_image_available.svg/300px-No_image_available.svg.png",
            descriptionEn = "First Arab winner of Man Booker International Prize",
            descriptionAr = "أول عربية تفوز بجائزة مان بوكر الدولية",
            biographyEn = "Jokha Alharthi is an Omani author who made history as the first Arabic-language writer to win the Man Booker International Prize in 2019 for her novel 'Celestial Bodies'.",
            biographyAr = "جوخة الحارثي كاتبة عُمانية صنعت التاريخ كأول كاتبة عربية تفوز بجائزة مان بوكر الدولية عام 2019 عن روايتها 'سيدات القمر'.",
            achievementsEn = listOf(
                "Won Man Booker International Prize 2019",
                "Author of 'Celestial Bodies'",
                "Professor at Sultan Qaboos University",
                "Multiple literary awards"
            ),
            achievementsAr = listOf(
                "فازت بجائزة مان بوكر الدولية 2019",
                "مؤلفة رواية 'سيدات القمر'",
                "أستاذة في جامعة السلطان قابوس",
                "جوائز أدبية متعددة"
            ),
            era = "1978-Present",
            yearsActive = 20,
            worksCount = 8
        ),
        
        // Additional Artists
        Figure(
            id = 14,
            nameEn = "Anwar Sonya",
            nameAr = "أنور سونيا",
            category = Category.ARTISTS,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/No_image_available.svg/300px-No_image_available.svg.png",
            descriptionEn = "Pioneer of Omani visual arts",
            descriptionAr = "رائد الفنون البصرية العُمانية",
            biographyEn = "Anwar Sonya is considered one of the pioneers of contemporary visual arts in Oman, known for blending traditional Omani themes with modern artistic techniques.",
            biographyAr = "يُعتبر أنور سونيا من رواد الفنون البصرية المعاصرة في عُمان، معروف بدمج الموضوعات العُمانية التقليدية مع التقنيات الفنية الحديثة.",
            achievementsEn = listOf(
                "Exhibited internationally",
                "Founded art education programs",
                "Preserved Omani artistic heritage"
            ),
            achievementsAr = listOf(
                "عرض أعماله دوليًا",
                "أسس برامج تعليم الفن",
                "حافظ على التراث الفني العُماني"
            ),
            era = "1948-Present",
            yearsActive = 50,
            worksCount = 200
        ),
        
        // Additional Sports Icons
        Figure(
            id = 15,
            nameEn = "Imad Al-Hosni",
            nameAr = "عماد الحوسني",
            category = Category.SPORTS,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/No_image_available.svg/300px-No_image_available.svg.png",
            descriptionEn = "Omani football captain and defender",
            descriptionAr = "قائد ومدافع المنتخب العُماني",
            biographyEn = "Imad Al-Hosni is a professional Omani footballer who has served as captain of the Oman national team. Known for his leadership and defensive skills.",
            biographyAr = "عماد الحوسني لاعب كرة قدم عُماني محترف شغل منصب قائد المنتخب العُماني. معروف بقيادته ومهاراته الدفاعية.",
            achievementsEn = listOf(
                "Captain of Oman national team",
                "Gulf Cup winner",
                "Over 80 international caps"
            ),
            achievementsAr = listOf(
                "قائد المنتخب العُماني",
                "فائز بكأس الخليج",
                "أكثر من 80 مباراة دولية"
            ),
            era = "1987-Present",
            yearsActive = 18,
            worksCount = 85
        ),
        Figure(
            id = 16,
            nameEn = "Mohsin Al-Ghassani",
            nameAr = "محسن الغساني",
            category = Category.SPORTS,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/No_image_available.svg/300px-No_image_available.svg.png",
            descriptionEn = "Omani rally driver champion",
            descriptionAr = "بطل سباقات الراليات العُماني",
            biographyEn = "Mohsin Al-Ghassani is a renowned Omani rally driver who has represented Oman in international motorsport competitions and won multiple regional championships.",
            biographyAr = "محسن الغساني سائق راليات عُماني مشهور مثّل عُمان في مسابقات رياضة السيارات الدولية وفاز ببطولات إقليمية متعددة.",
            achievementsEn = listOf(
                "Multiple Middle East Rally Championship titles",
                "Represented Oman in World Rally Championship",
                "Promoted motorsport in Oman"
            ),
            achievementsAr = listOf(
                "ألقاب متعددة في بطولة الشرق الأوسط للراليات",
                "مثّل عُمان في بطولة العالم للراليات",
                "روّج لرياضة السيارات في عُمان"
            ),
            era = "1975-Present",
            yearsActive = 30,
            worksCount = 45
        ),
        
        // Additional Scholars
        Figure(
            id = 17,
            nameEn = "Sheikh Ahmed al-Khalili",
            nameAr = "الشيخ أحمد الخليلي",
            category = Category.SCHOLARS,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/No_image_available.svg/300px-No_image_available.svg.png",
            descriptionEn = "Grand Mufti of Oman",
            descriptionAr = "المفتي العام لسلطنة عُمان",
            biographyEn = "Sheikh Ahmed bin Hamad al-Khalili is the Grand Mufti of Oman, the highest religious authority in the country. He is known for his scholarly works and moderate Islamic teachings.",
            biographyAr = "الشيخ أحمد بن حمد الخليلي هو المفتي العام لسلطنة عُمان، أعلى سلطة دينية في البلاد. معروف بأعماله العلمية وتعاليمه الإسلامية المعتدلة.",
            achievementsEn = listOf(
                "Appointed Grand Mufti in 1975",
                "Authored numerous Islamic scholarly works",
                "Promoted interfaith dialogue",
                "Established religious education programs"
            ),
            achievementsAr = listOf(
                "عُيّن مفتيًا عامًا عام 1975",
                "ألّف أعمالًا علمية إسلامية عديدة",
                "روّج للحوار بين الأديان",
                "أسس برامج التعليم الديني"
            ),
            era = "1942-Present",
            yearsActive = 49,
            worksCount = 35
        ),
        Figure(
            id = 18,
            nameEn = "Dr. Asila Al-Maamari",
            nameAr = "د. أصيلة المعمري",
            category = Category.SCHOLARS,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/No_image_available.svg/300px-No_image_available.svg.png",
            descriptionEn = "Omani scientist and researcher",
            descriptionAr = "عالمة وباحثة عُمانية",
            biographyEn = "Dr. Asila Al-Maamari is a prominent Omani scientist known for her research contributions in environmental science and sustainable development.",
            biographyAr = "د. أصيلة المعمري عالمة عُمانية بارزة معروفة بمساهماتها البحثية في علوم البيئة والتنمية المستدامة.",
            achievementsEn = listOf(
                "Published research in international journals",
                "Contributed to environmental policies",
                "Mentored young Omani scientists"
            ),
            achievementsAr = listOf(
                "نشرت أبحاثًا في مجلات دولية",
                "ساهمت في السياسات البيئية",
                "أرشدت العلماء العُمانيين الشباب"
            ),
            era = "1970-Present",
            yearsActive = 25,
            worksCount = 30
        ),
        
        // Additional Modern Influencers
        Figure(
            id = 19,
            nameEn = "Khalid Al-Sinani",
            nameAr = "خالد السناني",
            category = Category.MODERN,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/No_image_available.svg/300px-No_image_available.svg.png",
            descriptionEn = "Omani tech entrepreneur",
            descriptionAr = "رائد أعمال تقني عُماني",
            biographyEn = "Khalid Al-Sinani is a leading Omani tech entrepreneur who has founded multiple successful startups and contributed to Oman's digital transformation.",
            biographyAr = "خالد السناني رائد أعمال تقني عُماني رائد أسس شركات ناشئة ناجحة متعددة وساهم في التحول الرقمي لعُمان.",
            achievementsEn = listOf(
                "Founded successful tech startups",
                "Promoted digital innovation in Oman",
                "Mentored young entrepreneurs"
            ),
            achievementsAr = listOf(
                "أسس شركات تقنية ناجحة",
                "روّج للابتكار الرقمي في عُمان",
                "أرشد رواد الأعمال الشباب"
            ),
            era = "1985-Present",
            yearsActive = 15,
            worksCount = 8
        ),
        Figure(
            id = 20,
            nameEn = "Fatma Al-Nabhani",
            nameAr = "فاطمة النبهاني",
            category = Category.MODERN,
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/No_image_available.svg/300px-No_image_available.svg.png",
            descriptionEn = "Omani tennis pioneer",
            descriptionAr = "رائدة التنس العُمانية",
            biographyEn = "Fatma Al-Nabhani is a pioneering Omani tennis player who became the first Omani woman to compete professionally in international tennis tournaments.",
            biographyAr = "فاطمة النبهاني لاعبة تنس عُمانية رائدة أصبحت أول امرأة عُمانية تنافس باحتراف في بطولات التنس الدولية.",
            achievementsEn = listOf(
                "First Omani woman in professional tennis",
                "Represented Oman in Fed Cup",
                "Inspired women in sports",
                "Multiple national titles"
            ),
            achievementsAr = listOf(
                "أول امرأة عُمانية في التنس المحترف",
                "مثّلت عُمان في كأس الاتحاد",
                "ألهمت النساء في الرياضة",
                "ألقاب وطنية متعددة"
            ),
            era = "1991-Present",
            yearsActive = 15,
            worksCount = 25
        )
    )
    
    fun getAllFigures(): List<Figure> = figures
    
    fun getFigureById(id: Int): Figure? = figures.find { it.id == id }
    
    fun getFiguresByCategory(category: Category): List<Figure> = 
        figures.filter { it.category == category }
    
    fun getFiguresByIds(ids: List<Int>): List<Figure> = 
        figures.filter { it.id in ids }
    
    fun searchFigures(query: String): List<Figure> = 
        figures.filter { figure ->
            figure.nameEn.contains(query, ignoreCase = true) ||
            figure.nameAr.contains(query) ||
            figure.descriptionEn.contains(query, ignoreCase = true) ||
            figure.descriptionAr.contains(query)
        }
    
    fun getFeaturedFigure(): Figure = figures.first()
    
    fun getCategories(): List<Category> = Category.entries
}
