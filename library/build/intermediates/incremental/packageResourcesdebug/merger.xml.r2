<?xml version="1.0" encoding="utf-8"?>
<merger version="3"><dataSet config="main"><source path="E:\svnloadcode\Sqone\library\res"><file name="slide_in_from_bottom" path="E:\svnloadcode\Sqone\library\res\anim\slide_in_from_bottom.xml" qualifiers="" type="anim"/><file name="slide_in_from_top" path="E:\svnloadcode\Sqone\library\res\anim\slide_in_from_top.xml" qualifiers="" type="anim"/><file name="slide_out_to_bottom" path="E:\svnloadcode\Sqone\library\res\anim\slide_out_to_bottom.xml" qualifiers="" type="anim"/><file name="slide_out_to_top" path="E:\svnloadcode\Sqone\library\res\anim\slide_out_to_top.xml" qualifiers="" type="anim"/><file name="indicator_bg_bottom" path="E:\svnloadcode\Sqone\library\res\drawable\indicator_bg_bottom.xml" qualifiers="" type="drawable"/><file name="indicator_bg_top" path="E:\svnloadcode\Sqone\library\res\drawable\indicator_bg_top.xml" qualifiers="" type="drawable"/><file name="default_ptr_flip" path="E:\svnloadcode\Sqone\library\res\drawable-hdpi\default_ptr_flip.png" qualifiers="hdpi" type="drawable"/><file name="default_ptr_rotate" path="E:\svnloadcode\Sqone\library\res\drawable-hdpi\default_ptr_rotate.png" qualifiers="hdpi" type="drawable"/><file name="indicator_arrow" path="E:\svnloadcode\Sqone\library\res\drawable-hdpi\indicator_arrow.png" qualifiers="hdpi" type="drawable"/><file name="default_ptr_flip" path="E:\svnloadcode\Sqone\library\res\drawable-mdpi\default_ptr_flip.png" qualifiers="mdpi" type="drawable"/><file name="default_ptr_rotate" path="E:\svnloadcode\Sqone\library\res\drawable-mdpi\default_ptr_rotate.png" qualifiers="mdpi" type="drawable"/><file name="indicator_arrow" path="E:\svnloadcode\Sqone\library\res\drawable-mdpi\indicator_arrow.png" qualifiers="mdpi" type="drawable"/><file name="default_ptr_flip" path="E:\svnloadcode\Sqone\library\res\drawable-xhdpi\default_ptr_flip.png" qualifiers="xhdpi" type="drawable"/><file name="default_ptr_rotate" path="E:\svnloadcode\Sqone\library\res\drawable-xhdpi\default_ptr_rotate.png" qualifiers="xhdpi" type="drawable"/><file name="indicator_arrow" path="E:\svnloadcode\Sqone\library\res\drawable-xhdpi\indicator_arrow.png" qualifiers="xhdpi" type="drawable"/><file name="pull_to_refresh_header_horizontal" path="E:\svnloadcode\Sqone\library\res\layout\pull_to_refresh_header_horizontal.xml" qualifiers="" type="layout"/><file name="pull_to_refresh_header_vertical" path="E:\svnloadcode\Sqone\library\res\layout\pull_to_refresh_header_vertical.xml" qualifiers="" type="layout"/><file path="E:\svnloadcode\Sqone\library\res\values\attrs.xml" qualifiers=""><declare-styleable name="PullToRefresh">

        <!-- A drawable to use as the background of the Refreshable View -->
        <attr format="reference|color" name="ptrRefreshableViewBackground"/>

        <!-- A drawable to use as the background of the Header and Footer Loading Views -->
        <attr format="reference|color" name="ptrHeaderBackground"/>

        <!-- Text Color of the Header and Footer Loading Views -->
        <attr format="reference|color" name="ptrHeaderTextColor"/>

        <!-- Text Color of the Header and Footer Loading Views Sub Header -->
        <attr format="reference|color" name="ptrHeaderSubTextColor"/>

        <!-- Mode of Pull-to-Refresh that should be used -->
        <attr name="ptrMode">
            <flag name="disabled" value="0x0"/>
            <flag name="pullFromStart" value="0x1"/>
            <flag name="pullFromEnd" value="0x2"/>
            <flag name="both" value="0x3"/>
            <flag name="manualOnly" value="0x4"/>

            <!-- These last two are depreacted -->
            <flag name="pullDownFromTop" value="0x1"/>
            <flag name="pullUpFromBottom" value="0x2"/>
        </attr>

        <!-- Whether the Indicator overlay(s) should be used -->
        <attr format="reference|boolean" name="ptrShowIndicator"/>

        <!-- Drawable to use as Loading Indicator. Changes both Header and Footer. -->
        <attr format="reference" name="ptrDrawable"/>

        <!-- Drawable to use as Loading Indicator in the Header View. Overrides value set in ptrDrawable. -->
        <attr format="reference" name="ptrDrawableStart"/>

        <!-- Drawable to use as Loading Indicator in the Footer View. Overrides value set in ptrDrawable. -->
        <attr format="reference" name="ptrDrawableEnd"/>

        <!-- Whether Android's built-in Over Scroll should be utilised for Pull-to-Refresh. -->
        <attr format="reference|boolean" name="ptrOverScroll"/>

        <!-- Base text color, typeface, size, and style for Header and Footer Loading Views -->
        <attr format="reference" name="ptrHeaderTextAppearance"/>

        <!-- Base text color, typeface, size, and style for Header and Footer Loading Views Sub Header -->
        <attr format="reference" name="ptrSubHeaderTextAppearance"/>

        <!-- Style of Animation should be used displayed when pulling. -->
        <attr name="ptrAnimationStyle">
            <flag name="rotate" value="0x0"/>
            <flag name="flip" value="0x1"/>
        </attr>

        <!-- Whether the user can scroll while the View is Refreshing -->
        <attr format="reference|boolean" name="ptrScrollingWhileRefreshingEnabled"/>

        <!--
        	Whether PullToRefreshListView has it's extras enabled. This allows the user to be 
        	able to scroll while refreshing, and behaves better. It acheives this by adding
        	Header and/or Footer Views to the ListView.
        -->
        <attr format="reference|boolean" name="ptrListViewExtrasEnabled"/>

        <!--
        	Whether the Drawable should be continually rotated as you pull. This only
        	takes effect when using the 'Rotate' Animation Style.
        -->
        <attr format="reference|boolean" name="ptrRotateDrawableWhilePulling"/>

        <!-- BELOW HERE ARE DEPRECEATED. DO NOT USE. -->
        <attr format="reference|color" name="ptrAdapterViewBackground"/>
        <attr format="reference" name="ptrDrawableTop"/>
        <attr format="reference" name="ptrDrawableBottom"/>
    </declare-styleable></file><file path="E:\svnloadcode\Sqone\library\res\values\dimens.xml" qualifiers=""><dimen name="indicator_internal_padding">4dp</dimen><dimen name="indicator_corner_radius">12dp</dimen><dimen name="indicator_right_padding">10dp</dimen><dimen name="header_footer_left_right_padding">24dp</dimen><dimen name="header_footer_top_bottom_padding">12dp</dimen></file><file path="E:\svnloadcode\Sqone\library\res\values\ids.xml" qualifiers=""><item name="gridview" type="id"/><item name="webview" type="id"/><item name="scrollview" type="id"/></file><file path="E:\svnloadcode\Sqone\library\res\values\pull_refresh_strings.xml" qualifiers=""><string name="pull_to_refresh_from_bottom_pull_label">@string/pull_to_refresh_pull_label</string><string name="pull_to_refresh_pull_label">Pull to refresh…</string><string name="pull_to_refresh_release_label">Release to refresh…</string><string name="pull_to_refresh_refreshing_label">Loading…</string><string name="pull_to_refresh_from_bottom_refreshing_label">@string/pull_to_refresh_refreshing_label</string><string name="pull_to_refresh_from_bottom_release_label">@string/pull_to_refresh_release_label</string></file><file path="E:\svnloadcode\Sqone\library\res\values-ar\pull_refresh_strings.xml" qualifiers="ar"><string name="pull_to_refresh_release_label">اترك للتحديث…</string><string name="pull_to_refresh_pull_label">اسحب للتحديث…</string><string name="pull_to_refresh_refreshing_label">تحميل…</string></file><file path="E:\svnloadcode\Sqone\library\res\values-cs\pull_refresh_strings.xml" qualifiers="cs"><string name="pull_to_refresh_refreshing_label">Načítání…</string><string name="pull_to_refresh_release_label">Uvolněním aktualizujete…</string><string name="pull_to_refresh_pull_label">Tažením aktualizujete…</string></file><file path="E:\svnloadcode\Sqone\library\res\values-de\pull_refresh_strings.xml" qualifiers="de"><string name="pull_to_refresh_pull_label">Ziehen zum Aktualisieren…</string><string name="pull_to_refresh_refreshing_label">Laden…</string><string name="pull_to_refresh_release_label">Loslassen zum Aktualisieren…</string></file><file path="E:\svnloadcode\Sqone\library\res\values-es\pull_refresh_strings.xml" qualifiers="es"><string name="pull_to_refresh_pull_label">Tirar para actualizar…</string><string name="pull_to_refresh_refreshing_label">Cargando…</string><string name="pull_to_refresh_release_label">Soltar para actualizar…</string></file><file path="E:\svnloadcode\Sqone\library\res\values-fi\pull_refresh_strings.xml" qualifiers="fi"><string name="pull_to_refresh_from_bottom_refreshing_label">@string/pull_to_refresh_refreshing_label</string><string name="pull_to_refresh_refreshing_label">Päivitetään…</string><string name="pull_to_refresh_from_bottom_release_label">@string/pull_to_refresh_release_label</string><string name="pull_to_refresh_pull_label">Päivitä vetämällä alas…</string><string name="pull_to_refresh_from_bottom_pull_label">Päivitä vetämällä ylös…</string><string name="pull_to_refresh_release_label">Päivitä vapauttamalla…</string></file><file path="E:\svnloadcode\Sqone\library\res\values-fr\pull_refresh_strings.xml" qualifiers="fr"><string name="pull_to_refresh_pull_label">Tirez pour rafraîchir…</string><string name="pull_to_refresh_release_label">Relâcher pour rafraîchir…</string><string name="pull_to_refresh_refreshing_label">Chargement…</string></file><file path="E:\svnloadcode\Sqone\library\res\values-he\pull_refresh_strings.xml" qualifiers="he"><string name="pull_to_refresh_release_label">שחרר לרענון…</string><string name="pull_to_refresh_pull_label">משוך לרענון…</string><string name="pull_to_refresh_refreshing_label">טוען…</string></file><file path="E:\svnloadcode\Sqone\library\res\values-it\pull_refresh_strings.xml" qualifiers="it"><string name="pull_to_refresh_refreshing_label">Caricamento…</string><string name="pull_to_refresh_release_label">Rilascia per aggionare…</string><string name="pull_to_refresh_pull_label">Tira per aggiornare…</string></file><file path="E:\svnloadcode\Sqone\library\res\values-iw\pull_refresh_strings.xml" qualifiers="iw"><string name="pull_to_refresh_pull_label">משוך לרענון…</string><string name="pull_to_refresh_refreshing_label">טוען…</string><string name="pull_to_refresh_release_label">שחרר לרענון…</string></file><file path="E:\svnloadcode\Sqone\library\res\values-ja\pull_refresh_strings.xml" qualifiers="ja"><string name="pull_to_refresh_pull_label">画面を引っ張って…</string><string name="pull_to_refresh_refreshing_label">読み込み中…</string><string name="pull_to_refresh_release_label">指を離して更新…</string></file><file path="E:\svnloadcode\Sqone\library\res\values-ko\pull_refresh_strings.xml" qualifiers="ko"><string name="pull_to_refresh_refreshing_label">로드 중…</string><string name="pull_to_refresh_release_label">놓아서 새로 고침…</string><string name="pull_to_refresh_pull_label">당겨서 새로 고침…</string></file><file path="E:\svnloadcode\Sqone\library\res\values-nl\pull_refresh_strings.xml" qualifiers="nl"><string name="pull_to_refresh_release_label">Loslaten om te vernieuwen…</string><string name="pull_to_refresh_pull_label">Sleep om te vernieuwen…</string><string name="pull_to_refresh_refreshing_label">Laden…</string></file><file path="E:\svnloadcode\Sqone\library\res\values-pl\pull_refresh_strings.xml" qualifiers="pl"><string name="pull_to_refresh_refreshing_label">Wczytywanie…</string><string name="pull_to_refresh_pull_label">Pociągnij, aby odświeżyć…</string><string name="pull_to_refresh_release_label">Puść, aby odświeżyć…</string></file><file path="E:\svnloadcode\Sqone\library\res\values-pt\pull_refresh_strings.xml" qualifiers="pt"><string name="pull_to_refresh_refreshing_label">A carregar…</string><string name="pull_to_refresh_release_label">Liberação para atualizar…</string><string name="pull_to_refresh_pull_label">Puxe para atualizar…</string></file><file path="E:\svnloadcode\Sqone\library\res\values-pt-rBR\pull_refresh_strings.xml" qualifiers="pt-rBR"><string name="pull_to_refresh_release_label">Libere para atualizar…</string><string name="pull_to_refresh_refreshing_label">Carregando…</string><string name="pull_to_refresh_pull_label">Puxe para atualizar…</string></file><file path="E:\svnloadcode\Sqone\library\res\values-ro\pull_refresh_strings.xml" qualifiers="ro"><string name="pull_to_refresh_refreshing_label">Încărcare…</string><string name="pull_to_refresh_pull_label">Trage pentru a reîmprospăta…</string><string name="pull_to_refresh_release_label">Eliberează pentru a reîmprospăta…</string></file><file path="E:\svnloadcode\Sqone\library\res\values-ru\pull_refresh_strings.xml" qualifiers="ru"><string name="pull_to_refresh_release_label">Отпустите для обновления…</string><string name="pull_to_refresh_pull_label">Потяните для обновления…</string><string name="pull_to_refresh_refreshing_label">Загрузка…</string></file><file path="E:\svnloadcode\Sqone\library\res\values-zh\pull_refresh_strings.xml" qualifiers="zh"><string name="pull_to_refresh_release_label">放开以刷新…</string><string name="pull_to_refresh_refreshing_label">正在载入…</string><string name="pull_to_refresh_pull_label">下拉刷新…</string></file></source><source path="E:\svnloadcode\Sqone\library\build\generated\res\rs\debug"/><source path="E:\svnloadcode\Sqone\library\build\generated\res\resValues\debug"/></dataSet><dataSet config="debug"><source path="E:\svnloadcode\Sqone\library\build-types\debug\res"/></dataSet><mergedItems><configuration qualifiers=""><declare-styleable name="PullToRefresh"><attr format="reference|color" name="ptrRefreshableViewBackground"/><attr format="reference|color" name="ptrHeaderBackground"/><attr format="reference|color" name="ptrHeaderTextColor"/><attr format="reference|color" name="ptrHeaderSubTextColor"/><attr name="ptrMode"><flag name="disabled" value="0x0"/><flag name="pullFromStart" value="0x1"/><flag name="pullFromEnd" value="0x2"/><flag name="both" value="0x3"/><flag name="manualOnly" value="0x4"/><flag name="pullDownFromTop" value="0x1"/><flag name="pullUpFromBottom" value="0x2"/></attr><attr format="reference|boolean" name="ptrShowIndicator"/><attr format="reference" name="ptrDrawable"/><attr format="reference" name="ptrDrawableStart"/><attr format="reference" name="ptrDrawableEnd"/><attr format="reference|boolean" name="ptrOverScroll"/><attr format="reference" name="ptrHeaderTextAppearance"/><attr format="reference" name="ptrSubHeaderTextAppearance"/><attr name="ptrAnimationStyle"><flag name="rotate" value="0x0"/><flag name="flip" value="0x1"/></attr><attr format="reference|boolean" name="ptrScrollingWhileRefreshingEnabled"/><attr format="reference|boolean" name="ptrListViewExtrasEnabled"/><attr format="reference|boolean" name="ptrRotateDrawableWhilePulling"/><attr format="reference|color" name="ptrAdapterViewBackground"/><attr format="reference" name="ptrDrawableTop"/><attr format="reference" name="ptrDrawableBottom"/></declare-styleable></configuration></mergedItems></merger>