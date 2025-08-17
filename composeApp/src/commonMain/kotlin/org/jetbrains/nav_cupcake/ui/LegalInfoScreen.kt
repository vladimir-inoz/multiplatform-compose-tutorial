package org.jetbrains.nav_cupcake.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

val placeholderText = """
    Это пример длинного текста-заглушки, который можно использовать для тестирования интерфейса. 
    Иногда требуется заполнить экран большим количеством текста, чтобы проверить прокрутку, отступы, 
    выравнивание или визуальное восприятие макета на разных устройствах.

    Представьте, что здесь размещена статья или инструкция для пользователя. Текст состоит из 
    нескольких абзацев, каждый из которых содержит информацию, достаточную для проверки 
    поведения текстовых компонентов. 

    Первый абзац описывает назначение текста. Второй абзац может включать дополнительные 
    сведения, например, о том, как приложение будет вести себя при увеличении шрифта 
    или изменении ориентации экрана. Третий абзац нужен для того, чтобы увеличить общий 
    размер текста и сделать его более реалистичным.

    Можно продолжать добавлять строки, пока текст не займёт несколько экранов. Это позволит 
    протестировать вертикальную прокрутку, корректность переноса строк и обрезку текста, 
    если она предусмотрена. Также такой текст полезен для проверки работы тёмной темы 
    и отображения серого цвета на различных экранах.

    В заключение: данный текст — всего лишь заглушка. Его можно безопасно удалить и заменить 
    реальным содержимым, когда придёт время. До этого момента он поможет убедиться, 
    что интерфейс работает стабильно и предсказуемо.
""".trimIndent()

@Composable
fun LegalInfoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "Info",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = placeholderText,
            style = MaterialTheme.typography.titleLarge
        )
    }
}